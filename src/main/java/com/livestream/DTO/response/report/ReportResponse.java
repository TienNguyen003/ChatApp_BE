package com.livestream.DTO.response.report;

import com.livestream.DTO.response.livestream.LivestreamResponse;
import com.livestream.DTO.response.user.UserResponse;
import com.livestream.DTO.response.video.VideoResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse {
    int id;
    UserResponse reporter;
    UserResponse targetUser;
    LivestreamResponse livestream;
    VideoResponse video;
    String reason;
    String status;
    LocalDateTime createdAt;
}
