package com.livestream.DTO.request.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelCreationRequest {
    @NotBlank
    @Size(max = 255)
    String name;

    @Size(max = 5000)
    String description;

    @JsonProperty("avatar_url")
    @Size(max = 1024)
    String avatarUrl;

    @JsonProperty("banner_url")
    @Size(max = 1024)
    String bannerUrl;
}
