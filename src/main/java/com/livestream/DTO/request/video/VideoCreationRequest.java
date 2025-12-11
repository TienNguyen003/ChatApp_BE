package com.livestream.DTO.request.video;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoCreationRequest {
    int channelId;
    int categoryId;
    String title;
    String description;
    String duration;
    String videoUrl;
    String thumbnailUrl;
}
