package com.livestream.DTO.response.reward;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionResponse {
    int id;
    String code;
    String title;
    String description;
    String actionType;
    int targetValue;
    BigDecimal rewardCoins;
    RewardGiftInfo rewardGift;
    boolean active;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class RewardGiftInfo {
        Integer id;
        String name;
        String iconUrl;
    }
}
