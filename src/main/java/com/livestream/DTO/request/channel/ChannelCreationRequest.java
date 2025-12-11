package com.livestream.DTO.request.channel;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelCreationRequest {
    String name;
    String description;
    String avatarUrl;
    String bannerUrl;
}
