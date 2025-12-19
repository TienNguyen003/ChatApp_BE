package com.livestream.Entity.reward;

import com.livestream.Entity.gift.Gift;
import com.livestream.Entity.user.Users;
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
@Table(name = "user_rewards")
public class UserReward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    Mission mission;

    @Builder.Default
    @Column(nullable = false)
    BigDecimal coinAmount = BigDecimal.ZERO; // xu nhận được

    @ManyToOne
    @JoinColumn(name = "gift_id")
    Gift gift; // quà tặng kèm (nếu có)

    // CLAIMABLE, CLAIMED, EXPIRED
    String status;

    LocalDateTime claimedAt;

    LocalDateTime expiresAt;
}
