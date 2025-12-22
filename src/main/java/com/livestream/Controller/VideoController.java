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
import jakarta.validation.Valid;

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
    ApiResponse<VideoResponse> createVideo(@Valid @RequestBody VideoCreationRequest request) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.createVideo(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<VideoResponse> updateVideo(
            @PathVariable int id,
            @Valid @RequestBody VideoUpdateRequest request) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.updateVideo(id, request))
                .build();
    }

    @PutMapping("/{id}/view")
    ApiResponse<VideoResponse> incrementViewCount(@PathVariable int id) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.incrementViewCount(id))
                .build();
    }

    @PutMapping("/{id}/like")
    ApiResponse<VideoResponse> incrementLikeCount(@PathVariable int id) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.incrementLikeCount(id))
                .build();
    }

    @PutMapping("/{id}/dislike")
    ApiResponse<VideoResponse> incrementDislikeCount(@PathVariable int id) {
        return ApiResponse.<VideoResponse>builder()
                .result(videoService.incrementDislikeCount(id))
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
            @RequestParam String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<VideoResponse> pageData = videoService.getAllVideos(search, pageable);
        return ApiResponse.<List<VideoResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/channel/{channelId}")
    ApiResponse<List<VideoResponse>> getVideosByChannel(
            @PathVariable int channelId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<VideoResponse> pageData = videoService.getVideosByChannel(channelId, pageable);
        return ApiResponse.<List<VideoResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/category/{categoryId}")
    ApiResponse<List<VideoResponse>> getVideosByCategory(
            @PathVariable int categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<VideoResponse> pageData = videoService.getVideosByCategory(categoryId, pageable);
        return ApiResponse.<List<VideoResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteVideo(@PathVariable int id) {
        videoService.deleteVideo(id);
        return ApiResponse.<Void>builder().build();
    }
}
