package com.livestream.DTO.request.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupportTicketRequest {
    @NotBlank
    @Size(max = 50)
    String category;

    @NotBlank
    @Size(max = 255)
    String title;

    @Size(max = 5000)
    String description;

    @NotBlank
    @Size(max = 20)
    String priority; // LOW, MEDIUM, HIGH, URGENT
}
