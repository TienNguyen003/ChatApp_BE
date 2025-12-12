package com.livestream.DTO.response.channel;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StreamKeyResponse {
    String streamKey;
    String streamUrl;
    String message;
}
