package com.livestream.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livestream.DTO.request.subscription.SubscriptionPackageRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.subscription.SubscriptionPackageResponse;
import com.livestream.Service.subscription.SubscriptionPackageService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}channel-subscriptions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChannelSubscriptionController {
    SubscriptionPackageService subscriptionPackageService;

    @PostMapping
    ApiResponse<SubscriptionPackageResponse> createPackage(@Valid @RequestBody SubscriptionPackageRequest request) {
        return ApiResponse.<SubscriptionPackageResponse>builder()
                .result(subscriptionPackageService.createPackage(request))
                .build();
    }

    @GetMapping("/channel/{channelId}")
    ApiResponse<List<SubscriptionPackageResponse>> getChannelPackages(@PathVariable int channelId) {
        return ApiResponse.<List<SubscriptionPackageResponse>>builder()
                .result(subscriptionPackageService.getChannelPackages(channelId))
                .build();
    }

    @PutMapping("/{packageId}")
    ApiResponse<SubscriptionPackageResponse> updatePackage(
            @PathVariable int packageId,
            @Valid @RequestBody SubscriptionPackageRequest request) {
        return ApiResponse.<SubscriptionPackageResponse>builder()
                .result(subscriptionPackageService.updatePackage(packageId, request))
                .build();
    }

    @DeleteMapping("/{packageId}")
    ApiResponse<Void> deletePackage(@PathVariable int packageId) {
        subscriptionPackageService.deletePackage(packageId);
        return ApiResponse.<Void>builder()
                .message("Xóa gói thành công")
                .build();
    }

    @PostMapping("/{packageId}/toggle")
    ApiResponse<SubscriptionPackageResponse> togglePackageStatus(@PathVariable int packageId) {
        return ApiResponse.<SubscriptionPackageResponse>builder()
                .result(subscriptionPackageService.togglePackageStatus(packageId))
                .build();
    }
}
