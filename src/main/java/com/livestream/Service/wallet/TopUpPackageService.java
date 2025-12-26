package com.livestream.Service.wallet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.wallet.TopUpPackageRequest;
import com.livestream.DTO.response.wallet.TopUpPackageResponse;
import com.livestream.Entity.user.Users;
import com.livestream.Entity.wallet.TopUpPackage;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.wallet.TopUpPackageMapper;
import com.livestream.Repository.user.UserRepository;
import com.livestream.Repository.wallet.TopUpPackageRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopUpPackageService {
    TopUpPackageRepository topUpPackageRepository;
    TopUpPackageMapper topUpPackageMapper;
    WalletService walletService;
    UserRepository userRepository;

    public TopUpPackageResponse createPackage(TopUpPackageRequest request) {
        TopUpPackage topUpPackage = topUpPackageMapper.toTopUpPackage(request);
        topUpPackage = topUpPackageRepository.save(topUpPackage);
        return topUpPackageMapper.toTopUpPackageResponse(topUpPackage);
    }

    public TopUpPackageResponse updatePackage(Long id, TopUpPackageRequest request) {
        TopUpPackage topUpPackage = topUpPackageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        topUpPackageMapper.updateTopUpPackage(topUpPackage, request);
        topUpPackage = topUpPackageRepository.save(topUpPackage);
        return topUpPackageMapper.toTopUpPackageResponse(topUpPackage);
    }

    public void deletePackage(Long id) {
        TopUpPackage topUpPackage = topUpPackageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        topUpPackageRepository.delete(topUpPackage);
    }

    public TopUpPackageResponse getPackage(Long id) {
        TopUpPackage topUpPackage = topUpPackageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        return topUpPackageMapper.toTopUpPackageResponse(topUpPackage);
    }

    public Page<TopUpPackageResponse> getAllPackages(Pageable pageable) {
        return topUpPackageRepository.findAllByOrderByDisplayOrderAsc(pageable)
                .map(topUpPackageMapper::toTopUpPackageResponse);
    }

    public Page<TopUpPackageResponse> getActivePackages(Pageable pageable) {
        return topUpPackageRepository.findByIsActiveTrueOrderByDisplayOrderAsc(pageable)
                .map(topUpPackageMapper::toTopUpPackageResponse);
    }

    public TopUpPackageResponse togglePackageStatus(Long id) {
        TopUpPackage topUpPackage = topUpPackageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        topUpPackage.setIsActive(!topUpPackage.getIsActive());
        topUpPackage = topUpPackageRepository.save(topUpPackage);
        return topUpPackageMapper.toTopUpPackageResponse(topUpPackage);
    }

    public void purcharsePackage(Long id) {
        TopUpPackage topUpPackage = topUpPackageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));

        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        walletService.credit(user, topUpPackage.getPrice(), "TOP_UP_PACKAGE",
                "Nạp tiền mua gói: " + topUpPackage.getId(), "WALLET_TRANSFER");
    }
}
