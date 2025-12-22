package com.livestream.DTO.request.livestream;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LivestreamUpdateRequest {
    @Size(max = 255)
    String title;

    @Size(max = 50)
    String status;

    @JsonProperty("thumbnail_url")
    @Size(max = 1024)
    String thumbnailUrl;
}
