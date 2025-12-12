package com.livestream.DTO.request.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistVideoRequest {
    @JsonProperty("video_id")
    int videoId;
    
    int position;
}
