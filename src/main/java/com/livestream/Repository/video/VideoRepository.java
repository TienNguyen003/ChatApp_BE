package com.livestream.Repository.video;

import com.livestream.Entity.video.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Page<Video> findByChannelId(int channelId, Pageable pageable);

    Page<Video> findByCategoryId(int categoryId, Pageable pageable);

    @Query("SELECT v FROM Video v WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(v.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Video> searchVideos(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT v FROM Video v WHERE v.uploadedAt >= :cutoffDate " +
            "ORDER BY (v.views / (TIMESTAMPDIFF(HOUR, v.uploadedAt, CURRENT_TIMESTAMP) + 2.0)) DESC")
    Page<Video> findTrendingVideos(@Param("cutoffDate") LocalDateTime cutoffDate, Pageable pageable);

    Page<Video> findAllByOrderByViewsDesc(Pageable pageable);

    @Query("SELECT v FROM Video v WHERE v.category.id IN (SELECT DISTINCT v2.category.id FROM Video v2 WHERE v2.id IN :watchedVideoIds) AND v.id NOT IN :watchedVideoIds ORDER BY v.views DESC")
    Page<Video> findRecommendedVideos(@Param("watchedVideoIds") List<Integer> watchedVideoIds, Pageable pageable);

    Page<Video> findAllByOrderByUploadedAtDesc(Pageable pageable);

    @Query("SELECT v FROM Video v JOIN Follower f ON f.channel.id = v.channel.id WHERE f.follower.id = :followerId")
    Page<Video> findAllByFollowerId(int followerId, Pageable pageable);
}
