package com.livestream.Service.moderator;

import com.livestream.DTO.request.moderator.ModeratorRequest;
import com.livestream.DTO.response.moderator.ModeratorResponse;
import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.moderator.Moderator;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.moderator.ModeratorMapper;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.moderator.ModeratorRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ModeratorService {
    ModeratorRepository moderatorRepository;
    ChannelRepository channelRepository;
    UserRepository userRepository;
    ModeratorMapper moderatorMapper;

    public ModeratorResponse assignModerator(ModeratorRequest request) {
        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Channel channel = channelRepository.findById(request.getChannelId())
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        if (moderatorRepository.existsByUserIdAndChannelId(request.getUserId(), request.getChannelId())) {
            throw new AppException(ErrorCode.ALREADY_MODERATOR);
        }

        Moderator moderator = Moderator.builder()
                .user(user)
                .channel(channel)
                .assignedAt(LocalDateTime.now())
                .build();

        return moderatorMapper.toModeratorResponse(moderatorRepository.save(moderator));
    }

    public List<ModeratorResponse> getChannelModerators(int channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new AppException(ErrorCode.CHANNEL_NOT_EXISTED);
        }

        return moderatorRepository.findByChannelId(channelId).stream()
                .map(moderatorMapper::toModeratorResponse)
                .toList();
    }

    public void removeModerator(int userId, int channelId) {
        Moderator moderator = moderatorRepository.findByUserIdAndChannelId(userId, channelId)
                .orElseThrow(() -> new AppException(ErrorCode.MODERATOR_NOT_EXISTED));

        moderatorRepository.delete(moderator);
    }

    public boolean isModerator(int userId, int channelId) {
        return moderatorRepository.existsByUserIdAndChannelId(userId, channelId);
    }
}
