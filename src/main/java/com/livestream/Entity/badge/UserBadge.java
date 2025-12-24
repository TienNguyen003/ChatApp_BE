package com.livestream.Entity.badge;

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
@Table(name = "user_badges")
public class UserBadge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    Badge badge;

    LocalDateTime assignedAt;

    @Column(name = "current_value")
    Integer currentValue;

    LocalDateTime completedAt;

    @Column(nullable = false)
    @Builder.Default
    Boolean isCompleted = false;
}
