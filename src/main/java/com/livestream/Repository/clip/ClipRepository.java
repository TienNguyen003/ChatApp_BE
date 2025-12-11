package com.livestream.Repository.clip;

import com.livestream.Entity.clip.Clip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClipRepository extends JpaRepository<Clip, Integer> {
    Page<Clip> findByLivestreamId(int livestreamId, Pageable pageable);

    Page<Clip> findByChannelId(int channelId, Pageable pageable);
}
