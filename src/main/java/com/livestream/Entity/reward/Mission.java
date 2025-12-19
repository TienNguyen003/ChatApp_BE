package com.livestream.Entity.reward;

import com.livestream.Entity.gift.Gift;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "missions")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true)
    String code; // e.g., DAILY_CHECKIN, WATCH_10_MIN, SEND_1_CHAT

    String title;

    @Column(columnDefinition = "TEXT")
    String description;

    String actionType; // CHECKIN, WATCH, SEND_GIFT, LIVESTREAM

    int targetValue; // required count/amount to complete

    @Builder.Default
    @Column(nullable = false)
    BigDecimal rewardCoins = BigDecimal.ZERO; // xu thưởng

    @ManyToOne
    @JoinColumn(name = "reward_gift_id")
    Gift rewardGift; // quà tặng thêm (nếu có)

    boolean active;

    LocalDateTime startAt;

    LocalDateTime endAt;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
