package com.livestream.DTO.request.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WatchHistoryRequest {
    @JsonProperty("livestream_id")
    @Positive
    Integer livestreamId;

    @JsonProperty("video_id")
    @Positive
    Integer videoId;
}
