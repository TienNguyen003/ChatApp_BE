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

import com.livestream.DTO.request.clip.ClipCreationRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.clip.ClipResponse;
import com.livestream.Service.clip.ClipService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}clips")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClipController {
    ClipService clipService;

    @PostMapping
    ApiResponse<ClipResponse> createClip(@RequestBody ClipCreationRequest request) {
        return ApiResponse.<ClipResponse>builder()
                .result(clipService.createClip(request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ClipResponse> getClip(@PathVariable int id) {
        return ApiResponse.<ClipResponse>builder()
                .result(clipService.getClip(id))
                .build();
    }

    @GetMapping("/list")
    ApiResponse<List<ClipResponse>> getAllClips(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<ClipResponse> pageData = clipService.getAllClips(pageable);
        return ApiResponse.<List<ClipResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/livestream/{livestreamId}")
    ApiResponse<Page<ClipResponse>> getClipsByLivestream(
            @PathVariable int livestreamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<Page<ClipResponse>>builder()
                .result(clipService.getClipsByLivestream(livestreamId, pageable))
                .build();
    }

    @GetMapping("/channel/{channelId}")
    ApiResponse<Page<ClipResponse>> getClipsByChannel(
            @PathVariable int channelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<Page<ClipResponse>>builder()
                .result(clipService.getClipsByChannel(channelId, pageable))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteClip(@PathVariable int id) {
        clipService.deleteClip(id);
        return ApiResponse.<Void>builder().build();
    }
}
