package com.livestream.Service.history;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.history.WatchHistoryRequest;
import com.livestream.DTO.response.history.WatchHistoryResponse;
import com.livestream.Entity.history.WatchHistory;
import com.livestream.Entity.livestream.Livestream;
import com.livestream.Entity.user.Users;
import com.livestream.Entity.video.Video;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.history.WatchHistoryMapper;
import com.livestream.Repository.history.WatchHistoryRepository;
import com.livestream.Repository.livestream.LivestreamRepository;
import com.livestream.Repository.user.UserRepository;
import com.livestream.Repository.video.VideoRepository;
import com.livestream.Util.SpecificationBuilder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WatchHistoryService {
    WatchHistoryRepository watchHistoryRepository;
    LivestreamRepository livestreamRepository;
    VideoRepository videoRepository;
    UserRepository userRepository;
    WatchHistoryMapper watchHistoryMapper;
    SpecificationBuilder predicateBuilder;

    public WatchHistoryResponse addWatchHistory(WatchHistoryRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        WatchHistory watchHistory = watchHistoryMapper.toWatchHistory(request);
        watchHistory.setUser(user);
        watchHistory.setWatchedAt(LocalDateTime.now());

        if (request.getLivestreamId() != null) {
            Livestream livestream = livestreamRepository.findById(request.getLivestreamId())
                    .orElseThrow(() -> new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED));
            watchHistory.setLivestream(livestream);
        }

        if (request.getVideoId() != null) {
            Video video = videoRepository.findById(request.getVideoId())
                    .orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_EXISTED));
            watchHistory.setVideo(video);
        }

        return watchHistoryMapper.toWatchHistoryResponse(watchHistoryRepository.save(watchHistory));
    }

    public Page<WatchHistoryResponse> getMyWatchHistory(Map<String, Object> params, Pageable pageable) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Specification<WatchHistory> predicate = predicateBuilder.buildPredicate(params, WatchHistory.class);
        Specification<WatchHistory> userFilter = (root, query, cb) -> cb.equal(root.get("user").get("id"),
                user.getId());

        return watchHistoryRepository.findAll(Specification.where(userFilter).and(predicate), pageable)
                .map(watchHistoryMapper::toWatchHistoryResponse);
    }

    public void deleteWatchHistory(int id) {
        if (!watchHistoryRepository.existsById(id)) {
            throw new AppException(ErrorCode.WATCH_HISTORY_NOT_EXISTED);
        }
        watchHistoryRepository.deleteById(id);
    }
}
