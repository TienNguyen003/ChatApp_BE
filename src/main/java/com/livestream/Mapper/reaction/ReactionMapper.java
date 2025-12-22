package com.livestream.Mapper.reaction;

import org.mapstruct.Mapper;

import com.livestream.DTO.request.reaction.ReactionRequest;
import com.livestream.DTO.response.reaction.ReactionResponse;
import com.livestream.Entity.reaction.Reaction;
import com.livestream.Mapper.livestream.LivestreamMapper;
import com.livestream.Mapper.user.UserMapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class, LivestreamMapper.class })
public interface ReactionMapper {
    Reaction toReaction(ReactionRequest request);

    ReactionResponse toReactionResponse(Reaction reaction);
}
