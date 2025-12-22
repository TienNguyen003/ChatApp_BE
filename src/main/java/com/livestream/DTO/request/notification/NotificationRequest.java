package com.livestream.DTO.request.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    @JsonProperty("user_id")
    @Positive(message = "User ID must be greater than 0")
    int userId;

    @NotBlank(message = "Notification type is required")
    @Size(min = 2, max = 30, message = "Type must be between 2 and 30 characters")
    String type;

    @NotBlank(message = "Message is required")
    @Size(min = 1, max = 500, message = "Message must be between 1 and 500 characters")
    String message;
}
