package com.livestream.Entity.playlist;

import com.livestream.Entity.video.Video;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "playlist_id", "video_id" }))
public class PlaylistVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    Video video;

    @Column(nullable = false)
    int position;

    @Column(nullable = false)
    LocalDateTime addedAt;

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
    }
}
