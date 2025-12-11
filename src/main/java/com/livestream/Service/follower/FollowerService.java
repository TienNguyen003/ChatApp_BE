package com.livestream.Service.follower;

import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.follower.Follower;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.follower.FollowerRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FollowerService {
    FollowerRepository followerRepository;
    ChannelRepository channelRepository;
    UserRepository userRepository;

    public void followChannel(int channelId) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        if (followerRepository.existsByFollowerIdAndChannelId(user.getId(), channelId)) {
            throw new AppException(ErrorCode.ALREADY_FOLLOWING);
        }

        Follower follower = Follower.builder()
                .follower(user)
                .channel(channel)
                .createdAt(LocalDateTime.now())
                .build();

        followerRepository.save(follower);

        // Update followers count
        channel.setFollowersCount(channel.getFollowersCount() + 1);
        channelRepository.save(channel);
    }

    public void unfollowChannel(int channelId) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Follower follower = followerRepository.findByFollowerIdAndChannelId(user.getId(), channelId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOLLOWING));

        followerRepository.delete(follower);

        // Update followers count
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));
        channel.setFollowersCount(Math.max(0, channel.getFollowersCount() - 1));
        channelRepository.save(channel);
    }

    public boolean isFollowing(int channelId) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return followerRepository.existsByFollowerIdAndChannelId(user.getId(), channelId);
    }
}
