package com.livestream.DTO.request.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

import com.livestream.Entity.event.EventType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateRequest {
    @Size(min = 3, max = 100, message = "Event name must be between 3 and 100 characters")
    String name;

    EventType type;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    String description;

    @Size(max = 500, message = "Banner URL must not exceed 500 characters")
    String bannerUrl;

    LocalDateTime startAt;

    LocalDateTime endAt;

    Integer maxParticipants;

    @Size(max = 500, message = "Rules must not exceed 500 characters")
    String rules;

    @Size(max = 500, message = "Prize summary must not exceed 500 characters")
    String prizeSummary;
}
