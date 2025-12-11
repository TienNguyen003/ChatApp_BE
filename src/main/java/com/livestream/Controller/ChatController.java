package com.livestream.Controller;

import com.livestream.DTO.request.chat.ChatMessageRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.chat.ChatCommentResponse;
import com.livestream.Entity.chat.ChatComment;
import com.livestream.Entity.livestream.Livestream;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.chat.ChatCommentMapper;
import com.livestream.Repository.chat.ChatCommentRepository;
import com.livestream.Repository.livestream.LivestreamRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("${api.prefix}chat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {
    ChatCommentRepository chatCommentRepository;
    LivestreamRepository livestreamRepository;
    UserRepository userRepository;
    ChatCommentMapper chatCommentMapper;

    @PostMapping
    ApiResponse<ChatCommentResponse> sendMessage(@RequestBody ChatMessageRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Livestream livestream = livestreamRepository.findById(request.getLivestreamId())
                .orElseThrow(() -> new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED));

        ChatComment chatComment = chatCommentMapper.toChatComment(request);
        chatComment.setUser(user);
        chatComment.setLivestream(livestream);
        chatComment.setCreatedAt(LocalDateTime.now());

        return ApiResponse.<ChatCommentResponse>builder()
                .result(chatCommentMapper.toChatCommentResponse(chatCommentRepository.save(chatComment)))
                .build();
    }

    @GetMapping("/livestream/{livestreamId}")
    ApiResponse<Page<ChatCommentResponse>> getChatMessages(
            @PathVariable int livestreamId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return ApiResponse.<Page<ChatCommentResponse>>builder()
                .result(chatCommentRepository.findByLivestreamId(livestreamId, pageable)
                        .map(chatCommentMapper::toChatCommentResponse))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteMessage(@PathVariable int id) {
        chatCommentRepository.deleteById(id);
        return ApiResponse.<Void>builder().build();
    }
}
