package com.livestream.DTO.request.livestream;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LivestreamCreationRequest {
    @JsonProperty("channel_id")
    int channelId;

    @JsonProperty("category_id")
    int categoryId;

    String title;
    
    @JsonProperty("stream_url")
    String streamUrl;

    @JsonProperty("thumbnail_url")
    String thumbnailUrl;

    String description;
}
