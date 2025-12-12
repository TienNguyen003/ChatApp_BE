package com.livestream.Repository.livestream;

import com.livestream.Entity.livestream.Livestream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LivestreamRepository extends JpaRepository<Livestream, Integer> {
    Page<Livestream> findByChannelId(int channelId, Pageable pageable);

    Page<Livestream> findByStatus(String status, Pageable pageable);

    Page<Livestream> findByCategoryId(int categoryId, Pageable pageable);

    @Query("SELECT l FROM Livestream l WHERE LOWER(l.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Livestream> searchLivestreams(@Param("keyword") String keyword, Pageable pageable);

    Page<Livestream> findByStatusOrderByViewersCountDesc(String status, Pageable pageable);
}
