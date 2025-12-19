package com.livestream.DTO.response.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventParticipationResponse {
    int eventId;
    String status;
    Integer score;
    String result;
    LocalDateTime joinedAt;
    LocalDateTime completedAt;
}
