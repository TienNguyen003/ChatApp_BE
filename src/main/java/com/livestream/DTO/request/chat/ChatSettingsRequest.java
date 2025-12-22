package com.livestream.DTO.request.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatSettingsRequest {
    @JsonProperty("slow_mode_seconds")
    @Min(0)
    int slowModeSeconds;

    @JsonProperty("followers_only_mode")
    boolean followersOnlyMode;

    @JsonProperty("subscribers_only_mode")
    boolean subscribersOnlyMode;

    @JsonProperty("emotes_only_mode")
    boolean emotesOnlyMode;
}
