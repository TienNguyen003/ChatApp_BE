package com.livestream.DTO.response.notification;

import com.livestream.DTO.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {
    int id;
    UserResponse user;
    String type;
    String message;
    boolean isRead;
    LocalDateTime createdAt;
}
