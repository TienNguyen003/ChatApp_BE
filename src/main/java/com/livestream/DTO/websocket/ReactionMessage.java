package com.livestream.DTO.websocket;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactionMessage {
    String username;
    String reactionType; // LIKE, LOVE, WOW, HAHA, SAD, ANGRY
    int livestreamId;
    String timestamp;
}
