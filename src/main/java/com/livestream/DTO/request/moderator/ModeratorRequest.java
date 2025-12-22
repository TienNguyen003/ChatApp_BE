package com.livestream.DTO.request.moderator;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModeratorRequest {
    @JsonProperty("user_id")
    @Positive(message = "User ID must be greater than 0")
    int userId;

    @JsonProperty("channel_id")
    @Positive(message = "Channel ID must be greater than 0")
    int channelId;
}
