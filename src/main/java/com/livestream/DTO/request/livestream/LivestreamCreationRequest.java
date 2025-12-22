package com.livestream.DTO.request.livestream;

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
public class LivestreamCreationRequest {
    @JsonProperty("channel_id")
    @Positive
    int channelId;

    @JsonProperty("category_id")
    @Positive
    int categoryId;

    @NotBlank
    @Size(max = 255)
    String title;

    @JsonProperty("stream_url")
    @Size(max = 1024)
    String streamUrl;

    @JsonProperty("thumbnail_url")
    @Size(max = 1024)
    String thumbnailUrl;

    @Size(max = 5000)
    String description;
}
