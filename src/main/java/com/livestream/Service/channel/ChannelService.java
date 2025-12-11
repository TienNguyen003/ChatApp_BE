package com.livestream.Service.channel;

import com.livestream.DTO.request.channel.ChannelCreationRequest;
import com.livestream.DTO.request.channel.ChannelUpdateRequest;
import com.livestream.DTO.response.channel.ChannelResponse;
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
}
