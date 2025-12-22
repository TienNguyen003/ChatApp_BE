package com.livestream.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestream.DTO.request.playlist.PlaylistRequest;
import com.livestream.DTO.request.playlist.PlaylistVideoRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.playlist.PlaylistResponse;
import com.livestream.DTO.response.playlist.PlaylistVideoResponse;
import com.livestream.Service.playlist.PlaylistService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}playlists")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaylistController {
    PlaylistService playlistService;

    @PostMapping("/channel/{channelId}")
    ApiResponse<PlaylistResponse> createPlaylist(
            @PathVariable int channelId,
            @RequestBody PlaylistRequest request) {
        return ApiResponse.<PlaylistResponse>builder()
                .result(playlistService.createPlaylist(channelId, request))
                .build();
    }

    @PutMapping("/{playlistId}")
    ApiResponse<PlaylistResponse> updatePlaylist(
            @PathVariable int playlistId,
            @RequestBody PlaylistRequest request) {
        return ApiResponse.<PlaylistResponse>builder()
                .result(playlistService.updatePlaylist(playlistId, request))
                .build();
    }

    @DeleteMapping("/{playlistId}")
    ApiResponse<Void> deletePlaylist(@PathVariable int playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ApiResponse.<Void>builder()
                .message("Playlist deleted successfully")
                .build();
    }

    @PostMapping("/{playlistId}/videos")
    ApiResponse<PlaylistVideoResponse> addVideo(
            @PathVariable int playlistId,
            @RequestBody PlaylistVideoRequest request) {
        return ApiResponse.<PlaylistVideoResponse>builder()
                .result(playlistService.addVideoToPlaylist(playlistId, request))
                .build();
    }

    @DeleteMapping("/{playlistId}/videos/{videoId}")
    ApiResponse<Void> removeVideo(@PathVariable int playlistId, @PathVariable int videoId) {
        playlistService.removeVideoFromPlaylist(playlistId, videoId);
        return ApiResponse.<Void>builder()
                .message("Video removed from playlist")
                .build();
    }

    @GetMapping("/{playlistId}/videos")
    ApiResponse<List<PlaylistVideoResponse>> getPlaylistVideos(@PathVariable int playlistId) {
        return ApiResponse.<List<PlaylistVideoResponse>>builder()
                .result(playlistService.getPlaylistVideos(playlistId))
                .build();
    }

    @GetMapping("/channel/{channelId}")
    ApiResponse<Page<PlaylistResponse>> getChannelPlaylists(
            @PathVariable int channelId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.<Page<PlaylistResponse>>builder()
                .result(playlistService.getChannelPlaylists(channelId, page, limit))
                .build();
    }

    @GetMapping("/channel/{channelId}/my")
    ApiResponse<Page<PlaylistResponse>> getMyPlaylists(
            @PathVariable int channelId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.<Page<PlaylistResponse>>builder()
                .result(playlistService.getMyPlaylists(channelId, page, limit))
                .build();
    }
}
