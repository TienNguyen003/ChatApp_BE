package com.livestream.Entity.history;

import com.livestream.Entity.livestream.Livestream;
import com.livestream.Entity.user.Users;
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
@Table(name = "watch_history", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_video", columnNames = { "user_id", "video_id" }),
        @UniqueConstraint(name = "uk_user_livestream", columnNames = { "user_id", "livestream_id" })
})
public class WatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    @ManyToOne
    @JoinColumn(name = "livestream_id")
    Livestream livestream;

    @ManyToOne
    @JoinColumn(name = "video_id")
    Video video;

    LocalDateTime watchedAt;

    Integer progressSeconds;
    Integer durationSeconds;
    LocalDateTime lastViewedAt;
    Boolean completed;
}
