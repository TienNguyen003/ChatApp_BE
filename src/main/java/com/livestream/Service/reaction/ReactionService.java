package com.livestream.Service.reaction;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.reaction.ReactionRequest;
import com.livestream.DTO.response.reaction.ReactionResponse;
import com.livestream.Entity.livestream.Livestream;
import com.livestream.Entity.reaction.Reaction;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.reaction.ReactionMapper;
import com.livestream.Repository.livestream.LivestreamRepository;
import com.livestream.Repository.reaction.ReactionRepository;
import com.livestream.Repository.user.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReactionService {
    ReactionRepository reactionRepository;
    LivestreamRepository livestreamRepository;
    UserRepository userRepository;
    ReactionMapper reactionMapper;

    public ReactionResponse addReaction(ReactionRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Livestream livestream = livestreamRepository.findById(request.getLivestreamId())
                .orElseThrow(() -> new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED));

        Reaction reaction = reactionMapper.toReaction(request);
        reaction.setUser(user);
        reaction.setLivestream(livestream);
        reaction.setCreatedAt(LocalDateTime.now());

        return reactionMapper.toReactionResponse(reactionRepository.save(reaction));
    }

    public Page<ReactionResponse> getLivestreamReactions(int livestreamId, Pageable pageable) {
        if (!livestreamRepository.existsById(livestreamId)) {
            throw new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED);
        }

        return reactionRepository.findByLivestreamId(livestreamId, pageable)
                .map(reactionMapper::toReactionResponse);
    }

    public void deleteReaction(int id) {
        if (!reactionRepository.existsById(id)) {
            throw new AppException(ErrorCode.REACTION_NOT_EXISTED);
        }
        reactionRepository.deleteById(id);
    }
}
