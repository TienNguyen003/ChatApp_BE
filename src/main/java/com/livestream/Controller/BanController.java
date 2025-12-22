package com.livestream.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestream.DTO.request.ban.BanRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.ban.BanResponse;
import com.livestream.Service.ban.BanService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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
    ApiResponse<List<BanResponse>> getActiveBans(
            @PathVariable int channelId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Page<BanResponse> pageData = banService.getBannedUsers(channelId, page, limit);
        return ApiResponse.<List<BanResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
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
