package com.livestream.Service.subscription;

import com.livestream.DTO.request.subscription.SubscriptionPackageRequest;
import com.livestream.DTO.response.subscription.SubscriptionPackageResponse;
import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.subscription.SubscriptionPackage;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.subscription.SubscriptionPackageMapper;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.subscription.SubscriptionPackageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionPackageService {
    SubscriptionPackageRepository subscriptionPackageRepository;
    ChannelRepository channelRepository;
    SubscriptionPackageMapper subscriptionPackageMapper;

    @Transactional
    public SubscriptionPackageResponse createPackage(SubscriptionPackageRequest request) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        // Kiểm tra channel owner
        Channel channel = channelRepository.findAll().stream()
                .filter(c -> c.getUser() != null && c.getUser().getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        SubscriptionPackage pkg = SubscriptionPackage.builder()
                .channel(channel)
                .tierLevel(request.getTierLevel())
                .tierName(request.getTierName())
                .price(request.getPrice())
                .benefits(request.getBenefits() != null ? String.join(",", request.getBenefits()) : null)
                .description(request.getDescription())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        SubscriptionPackage saved = subscriptionPackageRepository.save(pkg);
        return subscriptionPackageMapper.toResponse(saved);
    }

    public List<SubscriptionPackageResponse> getChannelPackages(int channelId) {
        return subscriptionPackageRepository.findByChannelId(channelId).stream()
                .map(subscriptionPackageMapper::toResponse)
                .toList();
    }

    @Transactional
    public SubscriptionPackageResponse updatePackage(int packageId, SubscriptionPackageRequest request) {
        SubscriptionPackage pkg = subscriptionPackageRepository.findById(packageId)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));

        pkg.setTierName(request.getTierName());
        pkg.setPrice(request.getPrice());
        pkg.setBenefits(request.getBenefits() != null ? String.join(",", request.getBenefits()) : null);
        pkg.setDescription(request.getDescription());
        pkg.setUpdatedAt(LocalDateTime.now());

        SubscriptionPackage updated = subscriptionPackageRepository.save(pkg);
        return subscriptionPackageMapper.toResponse(updated);
    }

    @Transactional
    public void deletePackage(int packageId) {
        subscriptionPackageRepository.deleteById(packageId);
    }

    @Transactional
    public SubscriptionPackageResponse togglePackageStatus(int packageId) {
        SubscriptionPackage pkg = subscriptionPackageRepository.findById(packageId)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));

        pkg.setActive(!pkg.isActive());
        pkg.setUpdatedAt(LocalDateTime.now());

        SubscriptionPackage updated = subscriptionPackageRepository.save(pkg);
        return subscriptionPackageMapper.toResponse(updated);
    }
}
