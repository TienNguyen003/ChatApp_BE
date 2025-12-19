package com.livestream.Entity.wallet;

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
@Table(name = "wallet_transactions")
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    // DEPOSIT, WITHDRAW, PURCHASE, GIFT, MEMBERSHIP, REWARD
    String transactionType;

    @Column(nullable = false)
    BigDecimal amount;

    @Column(nullable = false)
    String currency;

    // PENDING, COMPLETED, FAILED, REFUNDED
    String status;

    String referenceType; // e.g., LIVESTREAM_GIFT, MEMBERSHIP, EVENT_REWARD

    String referenceId; // external/internal reference key

    String description;

    LocalDateTime createdAt;

    LocalDateTime completedAt;
}
