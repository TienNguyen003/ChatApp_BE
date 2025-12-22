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
import jakarta.validation.Valid;

import com.livestream.DTO.request.history.WatchHistoryRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.history.WatchHistoryResponse;
import com.livestream.Service.history.WatchHistoryService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}watch-history")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WatchHistoryController {
    WatchHistoryService watchHistoryService;

    @PostMapping
    ApiResponse<WatchHistoryResponse> addWatchHistory(@Valid @RequestBody WatchHistoryRequest request) {
        return ApiResponse.<WatchHistoryResponse>builder()
                .result(watchHistoryService.addWatchHistory(request))
                .build();
    }

    @GetMapping("/my-history")
    ApiResponse<List<WatchHistoryResponse>> getMyWatchHistory(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<WatchHistoryResponse> pageData = watchHistoryService.getMyWatchHistory(pageable);
        return ApiResponse.<List<WatchHistoryResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteWatchHistory(@PathVariable int id) {
        watchHistoryService.deleteWatchHistory(id);
        return ApiResponse.<Void>builder().build();
    }
}
