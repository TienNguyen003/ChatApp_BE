package com.livestream.DTO.request.video;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoCreationRequest {
    @JsonProperty("channel_id")
    int channelId;

    @JsonProperty("category_id")
    int categoryId;

    String title;

    String description;

    String duration;

    @JsonProperty("video_url")
    String videoUrl;

    @JsonProperty("thumbnail_url")
    String thumbnailUrl;
}
