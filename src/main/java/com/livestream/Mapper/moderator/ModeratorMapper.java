package com.livestream.Mapper.moderator;

import com.livestream.DTO.request.moderator.ModeratorRequest;
import com.livestream.DTO.response.moderator.ModeratorResponse;
import com.livestream.Entity.moderator.Moderator;
import com.livestream.Mapper.channel.ChannelMapper;
import com.livestream.Mapper.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, ChannelMapper.class })
public interface ModeratorMapper {
    Moderator toModerator(ModeratorRequest request);

    ModeratorResponse toModeratorResponse(Moderator moderator);
}
