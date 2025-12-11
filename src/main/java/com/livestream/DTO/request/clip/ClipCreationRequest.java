package com.livestream.DTO.request.clip;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClipCreationRequest {
    int livestreamId;
    String title;
    String startTime;
    String endTime;
}
