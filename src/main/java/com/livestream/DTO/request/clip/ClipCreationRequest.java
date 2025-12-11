package com.livestream.DTO.request.clip;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClipCreationRequest {
    @JsonProperty("livestream_id")
    int livestreamId;

    String title;

    @JsonProperty("start_time")
    String startTime;

    @JsonProperty("end_time")
    String endTime;

    @JsonProperty("video_url")
    String videoUrl;

    @JsonProperty("thumbnail_url")
    String thumbnailUrl;
}
