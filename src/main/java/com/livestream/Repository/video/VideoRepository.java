package com.livestream.Repository.video;

import com.livestream.Entity.video.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Page<Video> findByChannelId(int channelId, Pageable pageable);

    Page<Video> findByCategoryId(int categoryId, Pageable pageable);
}
