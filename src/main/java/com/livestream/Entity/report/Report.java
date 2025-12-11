package com.livestream.Entity.report;

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
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    Users reporter;

    @ManyToOne
    @JoinColumn(name = "target_user_id")
    Users targetUser;

    @ManyToOne
    @JoinColumn(name = "livestream_id")
    Livestream livestream;

    @ManyToOne
    @JoinColumn(name = "video_id")
    Video video;

    @Column(columnDefinition = "TEXT")
    String reason;

    String status;

    LocalDateTime createdAt;
}
