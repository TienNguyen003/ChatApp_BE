package com.livestream.Repository.history;

import com.livestream.Entity.history.WatchHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchHistoryRepository
        extends JpaRepository<WatchHistory, Integer>, JpaSpecificationExecutor<WatchHistory> {
    Page<WatchHistory> findByUserId(int userId, Specification<WatchHistory> spec, Pageable pageable);

    List<WatchHistory> findTop20ByUserUsernameOrderByWatchedAtDesc(String username);

    Optional<WatchHistory> findByUserIdAndVideoId(int userId, int videoId);

    Optional<WatchHistory> findByUserIdAndLivestreamId(int userId, int livestreamId);
}
