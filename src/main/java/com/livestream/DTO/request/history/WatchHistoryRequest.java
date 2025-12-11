package com.livestream.DTO.request.history;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WatchHistoryRequest {
    @JsonProperty("livestream_id")
    Integer livestreamId;

    @JsonProperty("video_id")
    Integer videoId;
}
