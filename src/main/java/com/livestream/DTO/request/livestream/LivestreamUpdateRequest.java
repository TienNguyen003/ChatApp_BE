package com.livestream.DTO.request.livestream;

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
    String thumbnailUrl;
}
