package com.livestream.DTO.response.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import com.livestream.Entity.event.EventType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventResponse {
    int id;
    String name;
    EventType type;
    String description;
    String bannerUrl;
    LocalDateTime startAt;
    LocalDateTime endAt;
    Integer maxParticipants;
    Integer currentParticipants;
    LocalDateTime publishedAt;
    String rules;
    String prizeSummary;
}
