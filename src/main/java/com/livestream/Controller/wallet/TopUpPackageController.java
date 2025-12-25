package com.livestream.Controller.wallet;

import com.livestream.DTO.request.wallet.TopUpPackageRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.wallet.TopUpPackageResponse;
import com.livestream.Service.wallet.TopUpPackageService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/top-up-packages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopUpPackageController {
    TopUpPackageService topUpPackageService;

    @PostMapping
    public ApiResponse<TopUpPackageResponse> createPackage(@Valid @RequestBody TopUpPackageRequest request) {
        return ApiResponse.<TopUpPackageResponse>builder()
                .result(topUpPackageService.createPackage(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TopUpPackageResponse> updatePackage(
            @PathVariable Long id,
            @Valid @RequestBody TopUpPackageRequest request) {
        return ApiResponse.<TopUpPackageResponse>builder()
                .result(topUpPackageService.updatePackage(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePackage(@PathVariable Long id) {
        topUpPackageService.deletePackage(id);
        return ApiResponse.<Void>builder()
                .message("Package deleted successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TopUpPackageResponse> getPackage(@PathVariable Long id) {
        return ApiResponse.<TopUpPackageResponse>builder()
                .result(topUpPackageService.getPackage(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<TopUpPackageResponse>> getAllPackages() {
        return ApiResponse.<List<TopUpPackageResponse>>builder()
                .result(topUpPackageService.getAllPackages())
                .build();
    }

    @GetMapping("/active")
    public ApiResponse<List<TopUpPackageResponse>> getActivePackages() {
        return ApiResponse.<List<TopUpPackageResponse>>builder()
                .result(topUpPackageService.getActivePackages())
                .build();
    }

    @PatchMapping("/{id}/toggle-status")
    public ApiResponse<TopUpPackageResponse> togglePackageStatus(@PathVariable Long id) {
        return ApiResponse.<TopUpPackageResponse>builder()
                .result(topUpPackageService.togglePackageStatus(id))
                .build();
    }
}
