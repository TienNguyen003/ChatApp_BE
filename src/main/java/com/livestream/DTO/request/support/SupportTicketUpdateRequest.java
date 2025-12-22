package com.livestream.DTO.request.support;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupportTicketUpdateRequest {
    @Size(max = 20)
    String status; // OPEN, IN_PROGRESS, RESOLVED, CLOSED

    @Size(max = 20)
    String priority; // LOW, MEDIUM, HIGH, URGENT

    @Positive
    Integer assigneeId;
}
