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

    // User progress fields (when fetched via getMyMissions)
    String status;
    Integer progressValue;
    LocalDateTime completedAt;
    LocalDateTime claimedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class RewardGiftInfo {
        Integer id;
        String name;
        String color;
        String iconUrl;
    }
}
