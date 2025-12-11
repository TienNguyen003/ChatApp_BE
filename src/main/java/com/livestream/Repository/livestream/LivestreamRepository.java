package com.livestream.Repository.livestream;

import com.livestream.Entity.livestream.Livestream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivestreamRepository extends JpaRepository<Livestream, Integer> {
    Page<Livestream> findByChannelId(int channelId, Pageable pageable);

    Page<Livestream> findByStatus(String status, Pageable pageable);

    Page<Livestream> findByCategoryId(int categoryId, Pageable pageable);
}
