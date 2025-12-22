package com.livestream.DTO.request.video;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoUpdateRequest {
    @Size(max = 255)
    String title;

    @Size(max = 5000)
    String description;

    @JsonProperty("thumbnail_url")
    @Size(max = 2048)
    String thumbnailUrl;

    @JsonProperty("tag_ids")
    Set<Integer> tagIds;
}
