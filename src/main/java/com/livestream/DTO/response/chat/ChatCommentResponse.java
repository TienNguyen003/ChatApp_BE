package com.livestream.DTO.response.chat;

import com.livestream.DTO.response.livestream.LivestreamResponse;
import com.livestream.DTO.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatCommentResponse {
    int id;
    LivestreamResponse livestream;
    UserResponse user;
    String message;
    LocalDateTime createdAt;
}
