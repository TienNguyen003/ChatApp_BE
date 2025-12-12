package com.livestream.Service.playlist;

import com.livestream.DTO.request.playlist.PlaylistRequest;
import com.livestream.DTO.request.playlist.PlaylistVideoRequest;
import com.livestream.DTO.response.playlist.PlaylistResponse;
import com.livestream.DTO.response.playlist.PlaylistVideoResponse;
import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.playlist.Playlist;
import com.livestream.Entity.playlist.PlaylistVideo;
import com.livestream.Entity.playlist.PlaylistVisibility;
import com.livestream.Entity.user.Users;
import com.livestream.Entity.video.Video;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.playlist.PlaylistRepository;
import com.livestream.Repository.playlist.PlaylistVideoRepository;
import com.livestream.Repository.user.UserRepository;
import com.livestream.Repository.video.VideoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaylistService {
    PlaylistRepository playlistRepository;
    PlaylistVideoRepository playlistVideoRepository;
    ChannelRepository channelRepository;
    VideoRepository videoRepository;
    UserRepository userRepository;

    public PlaylistResponse createPlaylist(int channelId, PlaylistRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        if (channel.getUser().getId() != currentUser.getId()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        Playlist playlist = Playlist.builder()
                .channel(channel)
                .name(request.getName())
                .description(request.getDescription())
                .visibility(request.getVisibility())
                .build();

        playlist = playlistRepository.save(playlist);

        return mapToResponse(playlist);
    }

    public PlaylistResponse updatePlaylist(int playlistId, PlaylistRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new AppException(ErrorCode.PLAYLIST_NOT_FOUND));

        if (playlist.getChannel().getUser().getId() != (currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        playlist.setName(request.getName());
        playlist.setDescription(request.getDescription());
        playlist.setVisibility(request.getVisibility());

        playlist = playlistRepository.save(playlist);

        return mapToResponse(playlist);
    }

    @Transactional
    public void deletePlaylist(int playlistId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new AppException(ErrorCode.PLAYLIST_NOT_FOUND));

        if (playlist.getChannel().getUser().getId() != (currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        playlistRepository.delete(playlist);
    }

    public PlaylistVideoResponse addVideoToPlaylist(int playlistId, PlaylistVideoRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new AppException(ErrorCode.PLAYLIST_NOT_FOUND));

        if (playlist.getChannel().getUser().getId() != (currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_EXISTED));

        // Check if video already in playlist
        playlistVideoRepository.findByPlaylistIdAndVideoId(playlistId, request.getVideoId())
                .ifPresent(pv -> {
                    throw new AppException(ErrorCode.VIDEO_ALREADY_IN_PLAYLIST);
                });

        PlaylistVideo playlistVideo = PlaylistVideo.builder()
                .playlist(playlist)
                .video(video)
                .position(request.getPosition())
                .build();

        playlistVideo = playlistVideoRepository.save(playlistVideo);

        return mapToVideoResponse(playlistVideo);
    }

    @Transactional
    public void removeVideoFromPlaylist(int playlistId, int videoId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new AppException(ErrorCode.PLAYLIST_NOT_FOUND));

        if (playlist.getChannel().getUser().getId() != (currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        playlistVideoRepository.deleteByPlaylistIdAndVideoId(playlistId, videoId);
    }

    public List<PlaylistVideoResponse> getPlaylistVideos(int playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new AppException(ErrorCode.PLAYLIST_NOT_FOUND));

        // Check visibility
        if (playlist.getVisibility() == PlaylistVisibility.PRIVATE) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Users currentUser = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            if (playlist.getChannel().getUser().getId() != (currentUser.getId())) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        }

        return playlistVideoRepository.findByPlaylistIdOrderByPosition(playlistId)
                .stream()
                .map(this::mapToVideoResponse)
                .collect(Collectors.toList());
    }

    public Page<PlaylistResponse> getChannelPlaylists(int channelId, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return playlistRepository.findByChannelIdAndVisibility(channelId, PlaylistVisibility.PUBLIC, pageable)
                .map(this::mapToResponse);
    }

    public Page<PlaylistResponse> getMyPlaylists(int channelId, int page, int limit) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        if (channel.getUser().getId() != (currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        Pageable pageable = PageRequest.of(page - 1, limit);
        return playlistRepository.findByChannelId(channelId, pageable)
                .map(this::mapToResponse);
    }

    private PlaylistResponse mapToResponse(Playlist playlist) {
        int videoCount = playlistVideoRepository.findByPlaylistIdOrderByPosition(playlist.getId()).size();

        return PlaylistResponse.builder()
                .id(playlist.getId())
                .channelId(playlist.getChannel().getId())
                .channelName(playlist.getChannel().getName())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .visibility(playlist.getVisibility())
                .videoCount(videoCount)
                .createdAt(playlist.getCreatedAt())
                .updatedAt(playlist.getUpdatedAt())
                .build();
    }

    private PlaylistVideoResponse mapToVideoResponse(PlaylistVideo pv) {
        return PlaylistVideoResponse.builder()
                .id(pv.getId())
                .playlistId(pv.getPlaylist().getId())
                .videoId(pv.getVideo().getId())
                .videoTitle(pv.getVideo().getTitle())
                .thumbnail(pv.getVideo().getThumbnailUrl())
                .duration(Integer.parseInt(pv.getVideo().getDuration()))
                .views(pv.getVideo().getViews())
                .position(pv.getPosition())
                .addedAt(pv.getAddedAt())
                .build();
    }
}
