package com.livestream.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestream.DTO.request.moderator.ModeratorRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.moderator.ModeratorResponse;
import com.livestream.Service.moderator.ModeratorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}moderators")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ModeratorController {
    ModeratorService moderatorService;

    @PostMapping
    ApiResponse<ModeratorResponse> assignModerator(@RequestBody ModeratorRequest request) {
        return ApiResponse.<ModeratorResponse>builder()
                .result(moderatorService.assignModerator(request))
                .build();
    }

    @GetMapping("/channel/{channelId}")
    ApiResponse<List<ModeratorResponse>> getChannelModerators(@PathVariable int channelId) {
        return ApiResponse.<List<ModeratorResponse>>builder()
                .result(moderatorService.getChannelModerators(channelId))
                .build();
    }

    @DeleteMapping
    ApiResponse<Void> removeModerator(
            @RequestParam int userId,
            @RequestParam int channelId) {
        moderatorService.removeModerator(userId, channelId);
        return ApiResponse.<Void>builder()
                .message("Gỡ moderator thành công")
                .build();
    }

    @GetMapping("/is-moderator")
    ApiResponse<Boolean> isModerator(
            @RequestParam int userId,
            @RequestParam int channelId) {
        return ApiResponse.<Boolean>builder()
                .result(moderatorService.isModerator(userId, channelId))
                .build();
    }
}
