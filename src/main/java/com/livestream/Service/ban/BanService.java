package com.livestream.Service.ban;

import com.livestream.DTO.request.ban.BanRequest;
import com.livestream.DTO.response.ban.BanResponse;
import com.livestream.Entity.ban.BanType;
import com.livestream.Entity.ban.ChannelBan;
import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Repository.ban.ChannelBanRepository;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BanService {
    ChannelBanRepository channelBanRepository;
    ChannelRepository channelRepository;
    UserRepository userRepository;

    public BanResponse banUser(int channelId, BanRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        if (channel.getUser().getId() != (currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        Users userToBan = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Check if already banned
        channelBanRepository.findActiveBan(channelId, request.getUserId(), LocalDateTime.now())
                .ifPresent(ban -> {
                    throw new AppException(ErrorCode.USER_ALREADY_BANNED);
                });

        // Validate expiry for temporary ban
        if (request.getBanType() == BanType.TEMPORARY && request.getExpiresAt() == null) {
            throw new AppException(ErrorCode.INVALID_BAN_EXPIRY);
        }

        ChannelBan ban = ChannelBan.builder()
                .channel(channel)
                .user(userToBan)
                .bannedBy(currentUser)
                .reason(request.getReason())
                .banType(request.getBanType())
                .expiresAt(request.getExpiresAt())
                .build();

        ban = channelBanRepository.save(ban);

        return mapToResponse(ban);
    }

    public void unbanUser(int channelId, int userId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        if (channel.getUser().getId() != (currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        ChannelBan ban = channelBanRepository.findActiveBan(channelId, userId, LocalDateTime.now())
                .orElseThrow(() -> new AppException(ErrorCode.BAN_NOT_FOUND));

        channelBanRepository.delete(ban);
    }

    public boolean checkBan(int channelId, int userId) {
        return channelBanRepository.findActiveBan(channelId, userId, LocalDateTime.now()).isPresent();
    }

    public Page<BanResponse> getBannedUsers(int channelId, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return channelBanRepository.findActiveBans(channelId, LocalDateTime.now(), pageable)
                .map(this::mapToResponse);
    }

    public Page<BanResponse> getAllBans(int channelId, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return channelBanRepository.findByChannelId(channelId, pageable)
                .map(this::mapToResponse);
    }

    private BanResponse mapToResponse(ChannelBan ban) {
        return BanResponse.builder()
                .id(ban.getId())
                .channelId(ban.getChannel().getId())
                .channelName(ban.getChannel().getName())
                .userId(ban.getUser().getId())
                .username(ban.getUser().getUsername())
                .bannedById(ban.getBannedBy().getId())
                .bannedByName(ban.getBannedBy().getUsername())
                .reason(ban.getReason())
                .banType(ban.getBanType())
                .expiresAt(ban.getExpiresAt())
                .createdAt(ban.getCreatedAt())
                .build();
    }
}
