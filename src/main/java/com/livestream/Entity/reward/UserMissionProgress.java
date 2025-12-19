package com.livestream.Entity.reward;

import com.livestream.Entity.user.Users;
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
@Table(name = "user_mission_progress")
public class UserMissionProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    Mission mission;

    int progressValue;

    // IN_PROGRESS, COMPLETED, CLAIMED, EXPIRED
    String status;

    LocalDateTime lastUpdatedAt;

    LocalDateTime completedAt;

    LocalDateTime claimedAt;
}
