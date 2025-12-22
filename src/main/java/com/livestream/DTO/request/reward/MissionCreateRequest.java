package com.livestream.DTO.request.reward;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionCreateRequest {
    @NotBlank(message = "Mission code is required")
    @Size(min = 2, max = 50, message = "Code must be between 2 and 50 characters")
    String code;

    @NotBlank(message = "Mission title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    String description;

    @NotBlank(message = "Action type is required")
    @Size(min = 2, max = 30, message = "Action type must be between 2 and 30 characters")
    String actionType; // CHECKIN, WATCH, SEND_GIFT, LIVESTREAM

    @Positive(message = "Target value must be greater than 0")
    int targetValue;

    @DecimalMin(value = "0.01", message = "Reward coins must be greater than 0")
    BigDecimal rewardCoins;

    @Positive(message = "Reward gift ID must be greater than 0")
    Integer rewardGiftId;

    @NotNull(message = "Start time is required")
    LocalDateTime startAt;

    @NotNull(message = "End time is required")
    LocalDateTime endAt;
}
