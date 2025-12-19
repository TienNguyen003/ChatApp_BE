package com.livestream.Controller;

import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.site.SiteInfoResponse;
import com.livestream.DTO.request.site.SiteInfoCreateRequest;
import com.livestream.Service.site.SiteInfoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}about")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SiteInfoController {
    SiteInfoService siteInfoService;

    @GetMapping("/{keyword}")
    ApiResponse<SiteInfoResponse> getByKeyword(@PathVariable String keyword) {
        return ApiResponse.<SiteInfoResponse>builder()
                .result(siteInfoService.getByKeyword(keyword))
                .build();
    }

    @PostMapping
    ApiResponse<SiteInfoResponse> create(@RequestBody SiteInfoCreateRequest request) {
        return ApiResponse.<SiteInfoResponse>builder()
                .result(siteInfoService.create(request.getKeyword(), request.getContent(), request.getUpdatedBy()))
                .build();
    }

    @PutMapping("/{keyword}")
    ApiResponse<SiteInfoResponse> upsert(
            @PathVariable String keyword,
            @RequestParam String content,
            @RequestParam(required = false) Integer updatedBy) {
        return ApiResponse.<SiteInfoResponse>builder()
                .result(siteInfoService.upsert(keyword, content, updatedBy))
                .build();
    }
}
