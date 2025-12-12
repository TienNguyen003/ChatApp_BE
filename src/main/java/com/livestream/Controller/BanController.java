package com.livestream.Controller;

import com.livestream.DTO.request.ban.BanRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.ban.BanResponse;
import com.livestream.Service.ban.BanService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}bans")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BanController {
    BanService banService;

    @PostMapping("/channel/{channelId}")
    ApiResponse<BanResponse> banUser(@PathVariable int channelId, @RequestBody BanRequest request) {
        return ApiResponse.<BanResponse>builder()
                .result(banService.banUser(channelId, request))
                .build();
    }

    @DeleteMapping("/channel/{channelId}/user/{userId}")
    ApiResponse<Void> unbanUser(@PathVariable int channelId, @PathVariable int userId) {
        banService.unbanUser(channelId, userId);
        return ApiResponse.<Void>builder()
                .message("User unbanned successfully")
                .build();
    }

    @GetMapping("/channel/{channelId}/check/{userId}")
    ApiResponse<Boolean> checkBan(@PathVariable int channelId, @PathVariable int userId) {
        return ApiResponse.<Boolean>builder()
                .result(banService.checkBan(channelId, userId))
                .build();
    }

    @GetMapping("/channel/{channelId}/active")
    ApiResponse<Page<BanResponse>> getActiveBans(
            @PathVariable int channelId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.<Page<BanResponse>>builder()
                .result(banService.getBannedUsers(channelId, page, limit))
                .build();
    }

    @GetMapping("/channel/{channelId}/all")
    ApiResponse<Page<BanResponse>> getAllBans(
            @PathVariable int channelId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.<Page<BanResponse>>builder()
                .result(banService.getAllBans(channelId, page, limit))
                .build();
    }
}
