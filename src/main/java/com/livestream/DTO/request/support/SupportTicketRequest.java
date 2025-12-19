package com.livestream.DTO.request.support;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupportTicketRequest {
    String category;
    String title;
    String description;
    String priority; // LOW, MEDIUM, HIGH, URGENT
}
