package com.livestream.DTO.response.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventResponse {
    int id;
    String name;
    String description;
    String bannerUrl;
    LocalDateTime startAt;
    LocalDateTime endAt;
    String rules;
    String prizeSummary;
}
