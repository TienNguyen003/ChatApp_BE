package com.livestream.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.livestream.DTO.request.chat.ChatSettingsRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.chat.ChatSettingsResponse;
import com.livestream.Service.chat.ChatRestrictionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}chat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatRestrictionController {
    ChatRestrictionService chatRestrictionService;

    @PutMapping("/livestream/{livestreamId}/settings")
    ApiResponse<ChatSettingsResponse> updateSettings(
            @PathVariable int livestreamId,
            @Valid @RequestBody ChatSettingsRequest request) {
        return ApiResponse.<ChatSettingsResponse>builder()
                .result(chatRestrictionService.updateChatSettings(livestreamId, request))
                .build();
    }

    @GetMapping("/livestream/{livestreamId}/settings")
    ApiResponse<ChatSettingsResponse> getSettings(@PathVariable int livestreamId) {
        return ApiResponse.<ChatSettingsResponse>builder()
                .result(chatRestrictionService.getChatSettings(livestreamId))
                .build();
    }

    @GetMapping("/livestream/{livestreamId}/can-send/{userId}")
    ApiResponse<Boolean> canSendMessage(@PathVariable int livestreamId, @PathVariable int userId) {
        return ApiResponse.<Boolean>builder()
                .result(chatRestrictionService.canSendMessage(livestreamId, userId))
                .build();
    }
}
