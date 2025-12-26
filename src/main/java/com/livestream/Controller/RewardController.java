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

import com.livestream.DTO.request.reward.MissionCreateRequest;
import com.livestream.DTO.request.reward.MissionUpdateRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.reward.MissionResponse;
import com.livestream.DTO.response.reward.UserRewardResponse;
import com.livestream.Service.reward.RewardService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}rewards")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RewardController {
    RewardService rewardService;

    @GetMapping("/missions")
    ApiResponse<List<MissionResponse>> listMissions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<MissionResponse> pageData = rewardService.listMissions(pageable);
        return ApiResponse.<List<MissionResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/my-missions")
    ApiResponse<List<MissionResponse>> getMyMissions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<MissionResponse> pageData = rewardService.getMyMissions(pageable);
        return ApiResponse.<List<MissionResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @PostMapping("/checkin")
    ApiResponse<UserRewardResponse> dailyCheckin() {
        return ApiResponse.<UserRewardResponse>builder()
                .result(rewardService.dailyCheckin())
                .build();
    }

    @PostMapping("/missions/{code}/claim")
    ApiResponse<UserRewardResponse> claimMission(@PathVariable String code) {
        return ApiResponse.<UserRewardResponse>builder()
                .result(rewardService.claimMission(code))
                .build();
    }

    // Admin CRUD endpoints
    @PostMapping("/missions")
    ApiResponse<MissionResponse> createMission(@Valid @RequestBody MissionCreateRequest request) {
        return ApiResponse.<MissionResponse>builder()
                .result(rewardService.createMission(request))
                .build();
    }

    @PutMapping("/missions/{id}")
    ApiResponse<MissionResponse> updateMission(
            @PathVariable Long id,
            @Valid @RequestBody MissionUpdateRequest request) {
        return ApiResponse.<MissionResponse>builder()
                .result(rewardService.updateMission(id, request))
                .build();
    }

    @DeleteMapping("/missions/{id}")
    ApiResponse<Void> deleteMission(@PathVariable Long id) {
        rewardService.deleteMission(id);
        return ApiResponse.<Void>builder().build();
    }
}
