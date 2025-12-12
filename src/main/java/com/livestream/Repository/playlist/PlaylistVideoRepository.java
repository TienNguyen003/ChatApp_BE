package com.livestream.Repository.playlist;

import com.livestream.Entity.playlist.PlaylistVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistVideoRepository extends JpaRepository<PlaylistVideo, Integer> {
    Optional<PlaylistVideo> findByPlaylistIdAndVideoId(int playlistId, int videoId);

    @Query("SELECT pv FROM PlaylistVideo pv WHERE pv.playlist.id = :playlistId ORDER BY pv.position")
    List<PlaylistVideo> findByPlaylistIdOrderByPosition(int playlistId);

    void deleteByPlaylistIdAndVideoId(int playlistId, int videoId);
}
