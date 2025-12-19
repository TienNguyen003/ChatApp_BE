package com.livestream.DTO.response.support;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupportTicketResponse {
    int id;
    String category;
    String title;
    String description;
    String status;
    String priority;
    LocalDateTime createdAt;
    LocalDateTime closedAt;
}
