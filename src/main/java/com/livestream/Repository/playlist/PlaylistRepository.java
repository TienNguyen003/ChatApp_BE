package com.livestream.Repository.playlist;

import com.livestream.Entity.playlist.Playlist;
import com.livestream.Entity.playlist.PlaylistVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    Page<Playlist> findByChannelIdAndVisibility(int channelId, PlaylistVisibility visibility, Pageable pageable);

    Page<Playlist> findByChannelId(int channelId, Pageable pageable);

    Page<Playlist> findByVisibility(PlaylistVisibility visibility, Pageable pageable);
}
