package com.livestream.Repository.history;

import com.livestream.Entity.history.WatchHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Integer> {
    Page<WatchHistory> findByUserId(int userId, Pageable pageable);

    List<WatchHistory> findTop20ByUserUsernameOrderByWatchedAtDesc(String username);
}
