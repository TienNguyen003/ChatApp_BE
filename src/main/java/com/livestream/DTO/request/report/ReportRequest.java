package com.livestream.DTO.request.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportRequest {
    @JsonProperty("target_user_id")
    Integer targetUserId;
    
    @JsonProperty("livestream_id")
    Integer livestreamId;

    @JsonProperty("video_id")
    Integer videoId;

    String reason;
}
