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

import com.livestream.DTO.request.subscription.SubscriptionRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.subscription.SubscriptionResponse;
import com.livestream.Service.subscription.SubscriptionService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}subscriptions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionController {
    SubscriptionService subscriptionService;

    @PostMapping
    ApiResponse<SubscriptionResponse> subscribe(@RequestBody SubscriptionRequest request) {
        return ApiResponse.<SubscriptionResponse>builder()
                .result(subscriptionService.subscribe(request))
                .build();
    }

    @GetMapping("/my-subscriptions")
    ApiResponse<List<SubscriptionResponse>> getMySubscriptions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<SubscriptionResponse> pageData = subscriptionService.getMySubscriptions(pageable);
        return ApiResponse.<List<SubscriptionResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/channel/{channelId}")
    ApiResponse<List<SubscriptionResponse>> getChannelSubscriptions(
            @PathVariable int channelId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<SubscriptionResponse> pageData = subscriptionService.getChannelSubscriptions(channelId, pageable);
        return ApiResponse.<List<SubscriptionResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @DeleteMapping("/cancel/{channelId}")
    ApiResponse<Void> cancelSubscription(@PathVariable int channelId) {
        subscriptionService.cancelSubscription(channelId);
        return ApiResponse.<Void>builder()
                .message("Hủy đăng ký thành công")
                .build();
    }
}
