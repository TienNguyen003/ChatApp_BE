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
public class MissionCreateRequest {
    String code;
    String title;
    String description;
    String actionType; // CHECKIN, WATCH, SEND_GIFT, LIVESTREAM
    int targetValue;
    BigDecimal rewardCoins;
    Integer rewardGiftId;
    LocalDateTime startAt;
    LocalDateTime endAt;
}
