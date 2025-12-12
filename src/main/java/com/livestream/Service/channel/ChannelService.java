package com.livestream.Service.channel;

import com.livestream.DTO.request.channel.ChannelCreationRequest;
import com.livestream.DTO.request.channel.ChannelUpdateRequest;
import com.livestream.DTO.response.channel.ChannelResponse;
import com.livestream.DTO.response.channel.StreamKeyResponse;
import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.channel.ChannelMapper;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChannelService {
    ChannelRepository channelRepository;
    UserRepository userRepository;
    ChannelMapper channelMapper;

    public ChannelResponse createChannel(ChannelCreationRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Check if user already has a channel
        if (channelRepository.findByUserId(user.getId()).isPresent()) {
            throw new AppException(ErrorCode.CHANNEL_ALREADY_EXISTED);
        }

        Channel channel = channelMapper.toChannel(request);
        channel.setUser(user);
        channel.setFollowersCount(0);
        channel.setStreamKey(generateStreamKey());
        channel.setCreatedAt(LocalDateTime.now());

        return channelMapper.toChannelResponse(channelRepository.save(channel));
    }

    public ChannelResponse updateChannel(int id, ChannelUpdateRequest request) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        channelMapper.updateChannel(channel, request);
        return channelMapper.toChannelResponse(channelRepository.save(channel));
    }

    public ChannelResponse getChannel(int id) {
        return channelMapper.toChannelResponse(
                channelRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED)));
    }

    public Page<ChannelResponse> getAllChannels(Pageable pageable) {
        return channelRepository.findAll(pageable)
                .map(channelMapper::toChannelResponse);
    }

    public void deleteChannel(int id) {
        if (!channelRepository.existsById(id)) {
            throw new AppException(ErrorCode.CHANNEL_NOT_EXISTED);
        }
        channelRepository.deleteById(id);
    }

    public StreamKeyResponse getStreamKey(int channelId) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        // Verify channel belongs to user
        if (channel.getUser().getId() != user.getId()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return StreamKeyResponse.builder()
                .streamKey(channel.getStreamKey())
                .streamUrl("rtmp://your-server.com/live/" + channel.getStreamKey())
                .message("Sử dụng stream key này trong OBS/Streamlabs để phát sóng")
                .build();
    }

    public StreamKeyResponse resetStreamKey(int channelId) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        // Verify channel belongs to user
        if (channel.getUser().getId() != user.getId()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        channel.setStreamKey(generateStreamKey());
        channelRepository.save(channel);

        return StreamKeyResponse.builder()
                .streamKey(channel.getStreamKey())
                .streamUrl("rtmp://your-server.com/live/" + channel.getStreamKey())
                .message("Stream key đã được tạo mới thành công")
                .build();
    }

    public boolean validateStreamKey(String streamKey) {
        return channelRepository.findByStreamKey(streamKey).isPresent();
    }

    private String generateStreamKey() {
        return "live_" + UUID.randomUUID().toString().replace("-", "");
    }
}
