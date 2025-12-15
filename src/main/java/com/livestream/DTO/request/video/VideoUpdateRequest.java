package com.livestream.DTO.request.video;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoUpdateRequest {
    String title;

    String description;

    @JsonProperty("thumbnail_url")
    String thumbnailUrl;

    @JsonProperty("tag_ids")
    Set<Integer> tagIds;
}
