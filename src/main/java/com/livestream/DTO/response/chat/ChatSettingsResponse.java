package com.livestream.DTO.response.chat;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatSettingsResponse {
    int livestreamId;
    int slowModeSeconds;
    boolean followersOnlyMode;
    boolean subscribersOnlyMode;
    boolean emotesOnlyMode;
}
