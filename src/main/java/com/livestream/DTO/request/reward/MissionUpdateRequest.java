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
public class MissionUpdateRequest {
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    String description;

    @Size(min = 2, max = 30, message = "Action type must be between 2 and 30 characters")
    String actionType;

    @Positive(message = "Target value must be greater than 0")
    Integer targetValue;

    @DecimalMin(value = "0.01", message = "Reward coins must be greater than 0")
    BigDecimal rewardCoins;

    @Positive(message = "Reward gift ID must be greater than 0")
    Integer rewardGiftId;

    Boolean active;

    LocalDateTime startAt;

    LocalDateTime endAt;
}
