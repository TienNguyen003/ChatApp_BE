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

import com.livestream.DTO.request.channel.ChannelCreationRequest;
import com.livestream.DTO.request.channel.ChannelUpdateRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.channel.ChannelResponse;
import com.livestream.DTO.response.channel.StreamKeyResponse;
import com.livestream.Service.channel.ChannelService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}channels")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChannelController {
    ChannelService channelService;

    @PostMapping
    ApiResponse<ChannelResponse> createChannel(@Valid @RequestBody ChannelCreationRequest request) {
        return ApiResponse.<ChannelResponse>builder()
                .result(channelService.createChannel(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ChannelResponse> updateChannel(
            @PathVariable int id,
            @Valid @RequestBody ChannelUpdateRequest request) {
        return ApiResponse.<ChannelResponse>builder()
                .result(channelService.updateChannel(id, request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ChannelResponse> getChannel(@PathVariable int id) {
        return ApiResponse.<ChannelResponse>builder()
                .result(channelService.getChannel(id))
                .build();
    }

    @GetMapping("/list")
    ApiResponse<List<ChannelResponse>> getAllChannels(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<ChannelResponse> pageData = channelService.getAllChannels(pageable);
        return ApiResponse.<List<ChannelResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteChannel(@PathVariable int id) {
        channelService.deleteChannel(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/{channelId}/stream-key")
    ApiResponse<StreamKeyResponse> getStreamKey(@PathVariable int channelId) {
        return ApiResponse.<StreamKeyResponse>builder()
                .result(channelService.getStreamKey(channelId))
                .build();
    }

    @PostMapping("/{channelId}/stream-key/reset")
    ApiResponse<StreamKeyResponse> resetStreamKey(@PathVariable int channelId) {
        return ApiResponse.<StreamKeyResponse>builder()
                .result(channelService.resetStreamKey(channelId))
                .build();
    }

    @GetMapping("/validate-stream-key")
    ApiResponse<Boolean> validateStreamKey(@RequestParam String streamKey) {
        return ApiResponse.<Boolean>builder()
                .result(channelService.validateStreamKey(streamKey))
                .build();
    }
}
