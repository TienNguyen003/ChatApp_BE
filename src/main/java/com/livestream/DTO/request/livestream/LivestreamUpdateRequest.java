package com.livestream.DTO.request.livestream;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LivestreamUpdateRequest {
    String title;
    
    String status;

    @JsonProperty("thumbnail_url")
    String thumbnailUrl;
}
