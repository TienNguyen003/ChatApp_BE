package com.livestream.DTO.websocket;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LivestreamStatus {
    int livestreamId;
    String status; // ONLINE, OFFLINE
    int viewersCount;
    String channelName;
}
