package com.livestream.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestream.DTO.request.reaction.ReactionRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.reaction.ReactionResponse;
import com.livestream.Service.reaction.ReactionService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}reactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReactionController {
    ReactionService reactionService;

    @PostMapping
    ApiResponse<ReactionResponse> addReaction(@RequestBody ReactionRequest request) {
        return ApiResponse.<ReactionResponse>builder()
                .result(reactionService.addReaction(request))
                .build();
    }

    @GetMapping("/livestream/{livestreamId}")
    ApiResponse<List<ReactionResponse>> getLivestreamReactions(
            @PathVariable int livestreamId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<ReactionResponse> pageData = reactionService.getLivestreamReactions(livestreamId, pageable);
        return ApiResponse.<List<ReactionResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteReaction(@PathVariable int id) {
        reactionService.deleteReaction(id);
        return ApiResponse.<Void>builder().build();
    }
}
