package com.livestream.DTO.request.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventCreateRequest {
    String name;

    String description;

    String bannerUrl;

    LocalDateTime startAt;

    LocalDateTime endAt;

    String rules;

    String prizeSummary;
}
