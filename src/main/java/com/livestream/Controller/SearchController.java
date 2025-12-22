package com.livestream.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.channel.ChannelResponse;
import com.livestream.DTO.response.livestream.LivestreamResponse;
import com.livestream.DTO.response.search.SearchResponse;
import com.livestream.DTO.response.video.VideoResponse;
import com.livestream.Service.search.SearchService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}search")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchController {
    SearchService searchService;

    @GetMapping
    ApiResponse<List<SearchResponse>> globalSearch(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<SearchResponse> pageData = searchService.globalSearch(keyword, pageable);
        return ApiResponse.<List<SearchResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/channels")
    ApiResponse<List<ChannelResponse>> searchChannels(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<ChannelResponse> pageData = searchService.searchChannels(keyword, pageable);
        return ApiResponse.<List<ChannelResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/livestreams")
    ApiResponse<List<LivestreamResponse>> searchLivestreams(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<LivestreamResponse> pageData = searchService.searchLivestreams(keyword, pageable);
        return ApiResponse.<List<LivestreamResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/videos")
    ApiResponse<List<VideoResponse>> searchVideos(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<VideoResponse> pageData = searchService.searchVideos(keyword, pageable);
        return ApiResponse.<List<VideoResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/trending/livestreams")
    ApiResponse<List<LivestreamResponse>> getTrendingLivestreams(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<LivestreamResponse> pageData = searchService.getTrendingLivestreams(pageable);
        return ApiResponse.<List<LivestreamResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/trending/videos")
    ApiResponse<List<VideoResponse>> getTrendingVideos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<VideoResponse> pageData = searchService.getTrendingVideos(pageable);
        return ApiResponse.<List<VideoResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/recommended")
    ApiResponse<List<VideoResponse>> getRecommendedVideos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<VideoResponse> pageData = searchService.getRecommendedVideos(pageable);
        return ApiResponse.<List<VideoResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/popular/channels")
    ApiResponse<List<ChannelResponse>> getPopularChannels(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<ChannelResponse> pageData = searchService.getPopularChannels(pageable);
        return ApiResponse.<List<ChannelResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }
}
