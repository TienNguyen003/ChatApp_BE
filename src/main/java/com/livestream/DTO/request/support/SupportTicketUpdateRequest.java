package com.livestream.DTO.request.support;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupportTicketUpdateRequest {
    String status; // OPEN, IN_PROGRESS, RESOLVED, CLOSED
    String priority; // LOW, MEDIUM, HIGH, URGENT
    Integer assigneeId;
}
