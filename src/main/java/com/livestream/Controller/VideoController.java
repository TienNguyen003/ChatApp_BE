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

import com.livestream.DTO.request.video.VideoCreationRequest;
import com.livestream.DTO.request.video.VideoUpdateRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.video.VideoResponse;
import com.livestream.Service.video.VideoService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}videos")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VideoController {
    VideoService videoService;

    @PostMapping
    ApiResponse<VideoResponse> createVideo(@RequestBody VideoCreationRequest request) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.createVideo(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<VideoResponse> updateVideo(
            @PathVariable int id,
            @RequestBody VideoUpdateRequest request) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.updateVideo(id, request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<VideoResponse> getVideo(@PathVariable int id) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.getVideo(id))
                .build();
    }

    @GetMapping("/list")
    ApiResponse<List<VideoResponse>> getAllVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoResponse> pageData = videoService.getAllVideos(pageable);
        return ApiResponse.<List<VideoResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/channel/{channelId}")
    ApiResponse<Page<VideoResponse>> getVideosByChannel(
            @PathVariable int channelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<Page<VideoResponse>>builder()
                .result(videoService.getVideosByChannel(channelId, pageable))
                .build();
    }

    @GetMapping("/category/{categoryId}")
    ApiResponse<Page<VideoResponse>> getVideosByCategory(
            @PathVariable int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<Page<VideoResponse>>builder()
                .result(videoService.getVideosByCategory(categoryId, pageable))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteVideo(@PathVariable int id) {
        videoService.deleteVideo(id);
        return ApiResponse.<Void>builder().build();
    }
}
