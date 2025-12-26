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
public class EventCreateRequest {
    @NotBlank(message = "Event name is required")
    @Size(min = 3, max = 100, message = "Event name must be between 3 and 100 characters")
    String name;

    @NotNull(message = "Event type is required")
    EventType type;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    String description;

    @Size(max = 500, message = "Banner URL must not exceed 500 characters")
    String bannerUrl;

    @NotNull(message = "Start time is required")
    LocalDateTime startAt;

    @NotNull(message = "End time is required")
    LocalDateTime endAt;

    Integer maxParticipants; // optional, null means unlimited

    @Size(max = 500, message = "Rules must not exceed 500 characters")
    String rules;

    @Size(max = 1000, message = "Prize summary must not exceed 1000 characters")
    String prizeSummary;
}
