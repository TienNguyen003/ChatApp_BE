package com.livestream.Controller;

import com.livestream.DTO.response.ApiResponse;
import com.livestream.Entity.follower.Follower;
import com.livestream.Service.follower.FollowerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}followers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FollowerController {
    FollowerService followerService;

    @PostMapping("/follow/{channelId}")
    ApiResponse<Void> followChannel(@PathVariable int channelId) {
        followerService.followChannel(channelId);
        return ApiResponse.<Void>builder()
                .message("Theo dõi kênh thành công")
                .build();
    }

    @DeleteMapping("/unfollow/{channelId}")
    ApiResponse<Void> unfollowChannel(@PathVariable int channelId) {
        followerService.unfollowChannel(channelId);
        return ApiResponse.<Void>builder()
                .message("Hủy theo dõi kênh thành công")
                .build();
    }

    @GetMapping("/is-following/{channelId}")
    ApiResponse<Boolean> isFollowing(@PathVariable int channelId) {
        return ApiResponse.<Boolean>builder()
                .result(followerService.isFollowing(channelId))
                .build();
    }

    @GetMapping("/channel/{channelId}")
    ApiResponse<Page<Follower>> getFollowersByChannel(
            @PathVariable int channelId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return ApiResponse.<Page<Follower>>builder()
                .result(followerService.getFollowersByChannel(channelId, pageable))
                .build();
    }
}
