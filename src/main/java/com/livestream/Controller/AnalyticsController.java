package com.livestream.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.analytics.AnalyticsResponse;
import com.livestream.DTO.response.analytics.DashboardResponse;
import com.livestream.Service.analytics.AnalyticsService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}analytics")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnalyticsController {
    AnalyticsService analyticsService;

    @GetMapping("/my-dashboard")
    ApiResponse<DashboardResponse> getMyDashboard() {
        return ApiResponse.<DashboardResponse>builder()
                .result(analyticsService.getMyDashboard())
                .build();
    }

    @GetMapping("/channel/{channelId}/dashboard")
    ApiResponse<DashboardResponse> getChannelDashboard(@PathVariable int channelId) {
        return ApiResponse.<DashboardResponse>builder()
                .result(analyticsService.getChannelDashboard(channelId))
                .build();
    }

    @GetMapping("/channel/{channelId}")
    ApiResponse<List<AnalyticsResponse>> getAnalytics(
            @PathVariable int channelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ApiResponse.<List<AnalyticsResponse>>builder()
                .result(analyticsService.getAnalytics(channelId, startDate, endDate))
                .build();
    }
}
