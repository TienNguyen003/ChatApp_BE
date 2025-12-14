package com.livestream.Service.search;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.livestream.DTO.response.channel.ChannelResponse;
import com.livestream.DTO.response.livestream.LivestreamResponse;
import com.livestream.DTO.response.search.SearchResponse;
import com.livestream.DTO.response.video.VideoResponse;
import com.livestream.Mapper.channel.ChannelMapper;
import com.livestream.Mapper.livestream.LivestreamMapper;
import com.livestream.Mapper.video.VideoMapper;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.history.WatchHistoryRepository;
import com.livestream.Repository.livestream.LivestreamRepository;
import com.livestream.Repository.video.VideoRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchService {
    ChannelRepository channelRepository;
    LivestreamRepository livestreamRepository;
    VideoRepository videoRepository;
    WatchHistoryRepository watchHistoryRepository;

    ChannelMapper channelMapper;
    LivestreamMapper livestreamMapper;
    VideoMapper videoMapper;

    public Page<SearchResponse> globalSearch(String keyword, Pageable pageable) {
        List<ChannelResponse> channels = channelRepository.searchChannels(keyword, pageable)
                .stream()
                .map(channelMapper::toChannelResponse)
                .collect(Collectors.toList());

        List<LivestreamResponse> livestreams = livestreamRepository.searchLivestreams(keyword, pageable)
                .stream()
                .map(livestreamMapper::toLivestreamResponse)
                .collect(Collectors.toList());

        List<VideoResponse> videos = videoRepository.searchVideos(keyword, pageable)
                .stream()
                .map(videoMapper::toVideoResponse)
                .collect(Collectors.toList());

        SearchResponse response = SearchResponse.builder()
                .channels(channels)
                .livestreams(livestreams)
                .videos(videos)
                .totalResults(channels.size() + livestreams.size() + videos.size())
                .build();
        
        return new PageImpl<>(List.of(response), pageable, 1);
    }

    public Page<ChannelResponse> searchChannels(String keyword, Pageable pageable) {
        return channelRepository.searchChannels(keyword, pageable)
                .map(channelMapper::toChannelResponse);
    }

    public Page<LivestreamResponse> searchLivestreams(String keyword, Pageable pageable) {
        return livestreamRepository.searchLivestreams(keyword, pageable)
                .map(livestreamMapper::toLivestreamResponse);
    }

    public Page<VideoResponse> searchVideos(String keyword, Pageable pageable) {
        return videoRepository.searchVideos(keyword, pageable)
                .map(videoMapper::toVideoResponse);
    }

    public Page<LivestreamResponse> getTrendingLivestreams(Pageable pageable) {
        return livestreamRepository.findByStatusOrderByViewersCountDesc("live", pageable)
                .map(livestreamMapper::toLivestreamResponse);
    }

    public Page<VideoResponse> getTrendingVideos(Pageable pageable) {
        // Videos with most views in last 7 days
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return videoRepository.findTrendingVideos(sevenDaysAgo, pageable)
                .map(videoMapper::toVideoResponse);
    }

    public Page<VideoResponse> getRecommendedVideos(Pageable pageable) {
        try {
            var context = SecurityContextHolder.getContext();
            String username = context.getAuthentication().getName();

            if (username == null || username.equals("anonymousUser")) {
                // Return popular videos for anonymous users
                return videoRepository.findAllByOrderByViewsDesc(pageable)
                        .map(videoMapper::toVideoResponse);
            }

            // Get user's watch history to find preferred categories
            List<Integer> watchedVideoIds = watchHistoryRepository.findTop20ByUserUsernameOrderByWatchedAtDesc(username)
                    .stream()
                    .filter(history -> history.getVideo() != null)
                    .map(history -> history.getVideo().getId())
                    .collect(Collectors.toList());

            if (watchedVideoIds.isEmpty()) {
                return videoRepository.findAllByOrderByViewsDesc(pageable)
                        .map(videoMapper::toVideoResponse);
            }

            // Find videos from same categories
            return videoRepository.findRecommendedVideos(watchedVideoIds, pageable)
                    .map(videoMapper::toVideoResponse);

        } catch (Exception e) {
            // Fallback to popular videos
            return videoRepository.findAllByOrderByViewsDesc(pageable)
                    .map(videoMapper::toVideoResponse);
        }
    }

    public Page<ChannelResponse> getPopularChannels(Pageable pageable) {
        return channelRepository.findAllByOrderByFollowersCountDesc(pageable)
                .map(channelMapper::toChannelResponse);
    }
}
