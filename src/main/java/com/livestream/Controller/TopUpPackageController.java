package com.livestream.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestream.DTO.request.wallet.TopUpPackageRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.wallet.TopUpPackageResponse;
import com.livestream.Service.wallet.TopUpPackageService;
import com.livestream.Util.PaginationUtil;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}top-up-packages")
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
    public ApiResponse<List<TopUpPackageResponse>> getAllPackages(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<TopUpPackageResponse> pageData = topUpPackageService.getAllPackages(pageable);
        return ApiResponse.<List<TopUpPackageResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/active")
    public ApiResponse<List<TopUpPackageResponse>> getActivePackages(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<TopUpPackageResponse> pageData = topUpPackageService.getAllPackages(pageable);
        return ApiResponse.<List<TopUpPackageResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @PatchMapping("/{id}/toggle-status")
    public ApiResponse<TopUpPackageResponse> togglePackageStatus(@PathVariable Long id) {
        return ApiResponse.<TopUpPackageResponse>builder()
                .result(topUpPackageService.togglePackageStatus(id))
                .build();
    }

    @PostMapping("/purcharse/{id}")
    public ApiResponse<String> postMethodName(@PathVariable Long id) {
        topUpPackageService.purcharsePackage(id);
        return ApiResponse.<String>builder()
                .result("Mua thành công")
                .build();
    }

}
