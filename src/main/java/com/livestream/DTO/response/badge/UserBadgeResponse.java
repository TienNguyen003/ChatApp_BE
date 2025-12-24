package com.livestream.DTO.response.badge;

import com.livestream.DTO.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBadgeResponse {
    int id;

    UserResponse user;

    BadgeResponse badge;

    LocalDateTime assignedAt;

    Integer currentValue;

    Boolean isCompleted;

    LocalDateTime completedAt;
}
