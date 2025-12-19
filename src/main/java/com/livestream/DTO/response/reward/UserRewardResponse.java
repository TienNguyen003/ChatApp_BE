package com.livestream.DTO.response.reward;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRewardResponse {
    int id;
    String missionCode;
    BigDecimal coinAmount;
    String status;
    LocalDateTime claimedAt;
}
