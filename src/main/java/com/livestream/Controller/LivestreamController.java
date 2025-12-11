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

import com.livestream.DTO.request.livestream.LivestreamCreationRequest;
import com.livestream.DTO.request.livestream.LivestreamUpdateRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.livestream.LivestreamResponse;
import com.livestream.Service.livestream.LivestreamService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}livestreams")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LivestreamController {
    LivestreamService livestreamService;

    @PostMapping
    ApiResponse<LivestreamResponse> createLivestream(@RequestBody LivestreamCreationRequest request) {
        return ApiResponse.<LivestreamResponse>builder()
                .result(livestreamService.createLivestream(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<LivestreamResponse> updateLivestream(
            @PathVariable int id,
            @RequestBody LivestreamUpdateRequest request) {
        return ApiResponse.<LivestreamResponse>builder()
                .result(livestreamService.updateLivestream(id, request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<LivestreamResponse> getLivestream(@PathVariable int id) {
        return ApiResponse.<LivestreamResponse>builder()
                .result(livestreamService.getLivestream(id))
                .build();
    }

    @GetMapping("/list")
    ApiResponse<List<LivestreamResponse>> getAllLivestreams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LivestreamResponse> pageData = livestreamService.getAllLivestreams(pageable);
        return ApiResponse.<List<LivestreamResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/channel/{channelId}")
    ApiResponse<Page<LivestreamResponse>> getLivestreamsByChannel(
            @PathVariable int channelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<Page<LivestreamResponse>>builder()
                .result(livestreamService.getLivestreamsByChannel(channelId, pageable))
                .build();
    }

    @GetMapping("/status/{status}")
    ApiResponse<Page<LivestreamResponse>> getLivestreamsByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<Page<LivestreamResponse>>builder()
                .result(livestreamService.getLivestreamsByStatus(status, pageable))
                .build();
    }

    @GetMapping("/category/{categoryId}")
    ApiResponse<Page<LivestreamResponse>> getLivestreamsByCategory(
            @PathVariable int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<Page<LivestreamResponse>>builder()
                .result(livestreamService.getLivestreamsByCategory(categoryId, pageable))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteLivestream(@PathVariable int id) {
        livestreamService.deleteLivestream(id);
        return ApiResponse.<Void>builder().build();
    }
}
