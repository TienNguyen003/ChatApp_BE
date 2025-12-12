package com.livestream.DTO.websocket;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage {
    String username;
    String avatar;
    String message;
    int livestreamId;
    String timestamp;
}
