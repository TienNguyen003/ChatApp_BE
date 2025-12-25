package com.livestream.Entity.payment;

import com.livestream.Entity.user.Users;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    // GIFT, SUBSCRIPTION, TOP_UP, INTERNAL_TRANSFER, GIFT_SPEND, REWARD, WITHDRAW
    String transactionType;

    double amount;

    String currency;

    String paymentMethod; // VNPAY, STRIPE, PAYPAL

    String status; // PENDING, COMPLETED, FAILED, REFUNDED

    String transactionId;

    String referenceType; // LIVESTREAM_GIFT, MEMBERSHIP, EVENT_REWARD, WALLET_TRANSFER

    String referenceId;

    String description;

    LocalDateTime createdAt;

    LocalDateTime completedAt;
}
