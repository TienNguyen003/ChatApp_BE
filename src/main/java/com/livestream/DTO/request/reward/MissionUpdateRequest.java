package com.livestream.DTO.request.reward;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionUpdateRequest {
    String title;
    String description;
    String actionType;
    Integer targetValue;
    BigDecimal rewardCoins;
    Integer rewardGiftId;
    Boolean active;
    LocalDateTime startAt;
    LocalDateTime endAt;
}
