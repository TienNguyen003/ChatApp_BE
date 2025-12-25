package com.livestream.Service.wallet;

import com.livestream.DTO.request.wallet.TopUpPackageRequest;
import com.livestream.DTO.response.wallet.TopUpPackageResponse;
import com.livestream.Entity.wallet.TopUpPackage;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.wallet.TopUpPackageMapper;
import com.livestream.Repository.wallet.TopUpPackageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopUpPackageService {
    TopUpPackageRepository topUpPackageRepository;
    TopUpPackageMapper topUpPackageMapper;

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

    public List<TopUpPackageResponse> getAllPackages() {
        return topUpPackageRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(topUpPackageMapper::toTopUpPackageResponse)
                .collect(Collectors.toList());
    }

    public List<TopUpPackageResponse> getActivePackages() {
        return topUpPackageRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(topUpPackageMapper::toTopUpPackageResponse)
                .collect(Collectors.toList());
    }

    public TopUpPackageResponse togglePackageStatus(Long id) {
        TopUpPackage topUpPackage = topUpPackageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        topUpPackage.setIsActive(!topUpPackage.getIsActive());
        topUpPackage = topUpPackageRepository.save(topUpPackage);
        return topUpPackageMapper.toTopUpPackageResponse(topUpPackage);
    }
}
