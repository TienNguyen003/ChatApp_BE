package com.livestream.DTO.request.video;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoCreationRequest {
    @JsonProperty("channel_id")
    @Positive
    int channelId;

    @JsonProperty("category_id")
    @Positive
    int categoryId;

    @NotBlank
    @Size(max = 255)
    String title;

    @Size(max = 5000)
    String description;

    @Size(max = 50)
    String duration;

    @JsonProperty("video_url")
    @NotBlank
    @Size(max = 2048)
    String videoUrl;

    @JsonProperty("thumbnail_url")
    @Size(max = 2048)
    String thumbnailUrl;

    @JsonProperty("tag_ids")
    Set<Integer> tagIds;

    @Builder.Default
    LocalDateTime uploadedAt = LocalDateTime.now();
}
