package com.livestream.DTO.response.reaction;

import com.livestream.DTO.response.livestream.LivestreamResponse;
import com.livestream.DTO.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactionResponse {
    int id;
    UserResponse user;
    LivestreamResponse livestream;
    String type;
    LocalDateTime createdAt;
}
