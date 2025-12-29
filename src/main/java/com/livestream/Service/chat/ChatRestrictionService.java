package com.livestream.Service.chat;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.chat.ChatSettingsRequest;
import com.livestream.DTO.response.chat.ChatSettingsResponse;
import com.livestream.Entity.livestream.Livestream;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Repository.ban.ChannelBanRepository;
import com.livestream.Repository.follower.FollowerRepository;
import com.livestream.Repository.livestream.LivestreamRepository;
import com.livestream.Repository.subscription.SubscriptionRepository;
import com.livestream.Repository.user.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatRestrictionService {
    LivestreamRepository livestreamRepository;
    UserRepository userRepository;
    ChannelBanRepository channelBanRepository;
    FollowerRepository followerRepository;
    SubscriptionRepository subscriptionRepository;
    RedisTemplate<String, String> redisTemplate;

    public ChatSettingsResponse updateChatSettings(int livestreamId, ChatSettingsRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Livestream livestream = livestreamRepository.findById(livestreamId)
                .orElseThrow(() -> new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED));

        if (livestream.getChannel().getUser().getId() != (currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        livestream.setSlowModeSeconds(request.getSlowModeSeconds());
        livestream.setFollowersOnlyMode(request.isFollowersOnlyMode());
        livestream.setSubscribersOnlyMode(request.isSubscribersOnlyMode());
        livestream.setEmotesOnlyMode(request.isEmotesOnlyMode());

        livestream = livestreamRepository.save(livestream);

        return mapToResponse(livestream);
    }

    public ChatSettingsResponse getChatSettings(int livestreamId) {
        Livestream livestream = livestreamRepository.findById(livestreamId)
                .orElseThrow(() -> new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED));

        return mapToResponse(livestream);
    }

    public boolean canSendMessage(int livestreamId, int userId) {
        Livestream livestream = livestreamRepository.findById(livestreamId)
                .orElseThrow(() -> new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED));

        userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Check if user is the channel owner
        if (livestream.getChannel().getUser().getId() == (userId)) {
            return true;
        }

        // Check if user is banned
        boolean isBanned = channelBanRepository.findActiveBan(
                livestream.getChannel().getId(),
                userId,
                LocalDateTime.now()).isPresent();

        if (isBanned) {
            throw new AppException(ErrorCode.USER_BANNED);
        }

        // Check slow mode
        if (livestream.getSlowModeSeconds() > 0) {
            String key = "slowmode:" + livestreamId + ":" + userId;
            String lastMessageTime = redisTemplate.opsForValue().get(key);

            if (lastMessageTime != null) {
                throw new AppException(ErrorCode.SLOW_MODE_ACTIVE);
            }

            // Set cooldown
            redisTemplate.opsForValue().set(
                    key,
                    String.valueOf(System.currentTimeMillis()),
                    livestream.getSlowModeSeconds(),
                    TimeUnit.SECONDS);
        }

        // Check followers only mode
        if (livestream.isFollowersOnlyMode()) {
            boolean isFollower = followerRepository.findByFollowerIdAndChannelId(
                    userId,
                    livestream.getChannel().getId()).isPresent();

            if (!isFollower) {
                throw new AppException(ErrorCode.FOLLOWERS_ONLY_MODE);
            }
        }

        // Check subscribers only mode
        if (livestream.isSubscribersOnlyMode()) {
            boolean isSubscriber = subscriptionRepository.findByUserId(
                    userId,
                    livestream.getChannel().getId()).isPresent();

            if (!isSubscriber) {
                throw new AppException(ErrorCode.SUBSCRIBERS_ONLY_MODE);
            }
        }

        return true;
    }

    private ChatSettingsResponse mapToResponse(Livestream livestream) {
        return ChatSettingsResponse.builder()
                .livestreamId(livestream.getId())
                .slowModeSeconds(livestream.getSlowModeSeconds())
                .followersOnlyMode(livestream.isFollowersOnlyMode())
                .subscribersOnlyMode(livestream.isSubscribersOnlyMode())
                .emotesOnlyMode(livestream.isEmotesOnlyMode())
                .build();
    }
}
