package com.livestream.DTO.response.history;

import com.livestream.DTO.response.livestream.LivestreamResponse;
import com.livestream.DTO.response.user.UserResponse;
import com.livestream.DTO.response.video.VideoResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WatchHistoryResponse {
    int id;
    UserResponse user;
    LivestreamResponse livestream;
    VideoResponse video;
    LocalDateTime watchedAt;
}
