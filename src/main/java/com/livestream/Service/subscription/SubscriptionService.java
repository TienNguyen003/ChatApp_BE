package com.livestream.Service.subscription;

import com.livestream.DTO.request.subscription.SubscriptionRequest;
import com.livestream.DTO.response.subscription.SubscriptionResponse;
import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.subscription.Subscription;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.subscription.SubscriptionMapper;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.subscription.SubscriptionRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionService {
    SubscriptionRepository subscriptionRepository;
    ChannelRepository channelRepository;
    UserRepository userRepository;
    SubscriptionMapper subscriptionMapper;

    public SubscriptionResponse subscribe(SubscriptionRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Channel channel = channelRepository.findById(request.getChannelId())
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        if (subscriptionRepository.findByUserIdAndChannelId(user.getId(), request.getChannelId()).isPresent()) {
            throw new AppException(ErrorCode.ALREADY_SUBSCRIBED);
        }

        Subscription subscription = subscriptionMapper.toSubscription(request);
        subscription.setUser(user);
        subscription.setChannel(channel);
        subscription.setStartedAt(LocalDateTime.now());

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

        return subscriptionRepository.findByChannelId(channelId, pageable)
                .map(subscriptionMapper::toSubscriptionResponse);
    }

    public void cancelSubscription(int channelId) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Subscription subscription = subscriptionRepository.findByUserIdAndChannelId(user.getId(), channelId)
                .orElseThrow(() -> new AppException(ErrorCode.SUBSCRIPTION_NOT_EXISTED));

        subscriptionRepository.delete(subscription);
    }
}
