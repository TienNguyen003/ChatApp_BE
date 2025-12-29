package com.livestream.Service.subscription;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.subscription.SubscriptionRequest;
import com.livestream.DTO.response.subscription.SubscriptionResponse;
import com.livestream.DTO.response.subscription.SubscriptionPackageResponse;
import com.livestream.Entity.subscription.Subscription;
import com.livestream.Entity.subscription.SubscriptionPackage;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.subscription.SubscriptionMapper;
import com.livestream.Mapper.subscription.SubscriptionPackageMapper;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.subscription.SubscriptionPackageRepository;
import com.livestream.Repository.subscription.SubscriptionRepository;
import com.livestream.Repository.user.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionService {
    SubscriptionRepository subscriptionRepository;
    ChannelRepository channelRepository;
    UserRepository userRepository;
    SubscriptionMapper subscriptionMapper;
    SubscriptionPackageRepository subscriptionPackageRepository;
    SubscriptionPackageMapper subscriptionPackageMapper;

    public SubscriptionResponse subscribe(SubscriptionRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        SubscriptionPackage subscriptionPackage = subscriptionPackageRepository.findById(request.getPackageId())
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));

        if (subscriptionRepository.findByUserIdAndSubscriptionPackage_Id(user.getId(), request.getPackageId())
                .isPresent()) {
            throw new AppException(ErrorCode.ALREADY_SUBSCRIBED);
        }

        Subscription subscription = subscriptionMapper.toSubscription(request);
        subscription.setUser(user);
        subscription.setSubscriptionPackage(subscriptionPackage);
        subscription.setStartedAt(LocalDateTime.now());
        subscription.setStatus("ACTIVE");
        subscription.setCreatedAt(LocalDateTime.now());
        subscription.setUpdatedAt(LocalDateTime.now());

        return subscriptionMapper.toSubscriptionResponse(subscriptionRepository.save(subscription));
    }

    public Page<SubscriptionResponse> getMySubscriptions(Pageable pageable) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return subscriptionRepository.findByUserId(user.getId(), pageable)
                .map(subscriptionMapper::toSubscriptionResponse);
    }

    public Page<SubscriptionResponse> getChannelSubscriptions(int channelId, Pageable pageable) {
        if (!channelRepository.existsById(channelId)) {
            throw new AppException(ErrorCode.CHANNEL_NOT_EXISTED);
        }

        return subscriptionRepository.findBySubscriptionPackage_Channel_Id(channelId, pageable)
                .map(subscriptionMapper::toSubscriptionResponse);
    }

    public void cancelSubscription(int subscriptionId) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .filter(sub -> sub.getUser().getId() == user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.SUBSCRIPTION_NOT_EXISTED));

        subscriptionRepository.delete(subscription);
    }

    public SubscriptionPackageResponse getPackageInfo(int packageId) {
        SubscriptionPackage subscriptionPackage = subscriptionPackageRepository.findById(packageId)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_FOUND));
        return subscriptionPackageMapper.toResponse(subscriptionPackage);
    }
}
