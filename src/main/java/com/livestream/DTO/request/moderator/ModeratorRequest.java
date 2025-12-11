package com.livestream.DTO.request.moderator;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModeratorRequest {
    @JsonProperty("user_id")
    int userId;

    @JsonProperty("channel_id")
    int channelId;
}
