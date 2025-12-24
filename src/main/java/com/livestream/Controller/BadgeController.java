package com.livestream.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.livestream.DTO.request.badge.AssignBadgeRequest;
import com.livestream.DTO.request.badge.BadgeRequest;
import com.livestream.DTO.request.badge.UpdateUserBadgeRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.badge.BadgeResponse;
import com.livestream.DTO.response.badge.UserBadgeResponse;
import com.livestream.Service.badge.BadgeService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}badges")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BadgeController {
    BadgeService badgeService;

    @PostMapping
    ApiResponse<BadgeResponse> createBadge(@Valid @RequestBody BadgeRequest request) {
        return ApiResponse.<BadgeResponse>builder()
                .result(badgeService.createBadge(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<BadgeResponse> updateBadge(
            @PathVariable int id,
            @Valid @RequestBody BadgeRequest request) {
        return ApiResponse.<BadgeResponse>builder()
                .result(badgeService.updateBadge(id, request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<BadgeResponse> getBadge(@PathVariable int id) {
        return ApiResponse.<BadgeResponse>builder()
                .result(badgeService.getBadge(id))
                .build();
    }

    @GetMapping("/getAll")
    ApiResponse<List<BadgeResponse>> getAllBadgesNoPagination() {
        return ApiResponse.<List<BadgeResponse>>builder()
                .result(badgeService.getAllBadges())
                .build();
    }

    @GetMapping("/list")
    ApiResponse<List<BadgeResponse>> getAllBadges(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<BadgeResponse> pageData = badgeService.getAllBadges(pageable);
        return ApiResponse.<List<BadgeResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteBadge(@PathVariable int id) {
        badgeService.deleteBadge(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/assign")
    ApiResponse<UserBadgeResponse> assignBadgeToUser(@RequestBody AssignBadgeRequest request) {
        return ApiResponse.<UserBadgeResponse>builder()
                .result(badgeService.assignBadgeToUser(request))
                .build();
    }

    @GetMapping("/user/{userId}")
    ApiResponse<List<UserBadgeResponse>> getUserBadges(@PathVariable int userId) {
        return ApiResponse.<List<UserBadgeResponse>>builder()
                .result(badgeService.getUserBadges(userId))
                .build();
    }

    @PutMapping("/user-badge/update")
    ApiResponse<UserBadgeResponse> updateUserBadge(@Valid @RequestBody UpdateUserBadgeRequest request) {
        return ApiResponse.<UserBadgeResponse>builder()
                .result(badgeService.updateUserBadge(request))
                .build();
    }
}
