package com.livestream.DTO.request.channel;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty("avatar_url")
    String avatarUrl;

    @JsonProperty("banner_url")
    String bannerUrl;
}
