package com.livestream.DTO.response.payment;

import com.livestream.DTO.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {
    int id;
    UserResponse user;
    String transactionType;
    double amount;
    String currency;
    String paymentMethod;
    String status;
    String transactionId;
    String referenceType;
    String referenceId;
    String description;
    LocalDateTime createdAt;
    LocalDateTime completedAt;
}
