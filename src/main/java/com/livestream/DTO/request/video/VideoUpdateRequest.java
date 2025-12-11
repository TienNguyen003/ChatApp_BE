package com.livestream.DTO.request.video;

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
    String thumbnailUrl;
}
