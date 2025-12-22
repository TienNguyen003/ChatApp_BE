package com.livestream.Mapper.channel;

import com.livestream.DTO.request.channel.ChannelCreationRequest;
import com.livestream.DTO.request.channel.ChannelUpdateRequest;
import com.livestream.DTO.response.channel.ChannelResponse;
import com.livestream.Entity.channel.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChannelMapper {
//    @Mapping(target = "user", ignore = true)
    Channel toChannel(ChannelCreationRequest request);

    ChannelResponse toChannelResponse(Channel channel);

    void updateChannel(@MappingTarget Channel channel, ChannelUpdateRequest request);
}
