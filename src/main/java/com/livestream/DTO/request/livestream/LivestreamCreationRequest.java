package com.livestream.DTO.request.livestream;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LivestreamCreationRequest {
    int channelId;
    int categoryId;
    String title;
    String streamUrl;
    String thumbnailUrl;
}
