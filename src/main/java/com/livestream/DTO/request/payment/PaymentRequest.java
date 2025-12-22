package com.livestream.DTO.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
    @JsonProperty("transaction_type")
    @NotBlank(message = "Transaction type is required")
    @Size(min = 3, max = 20, message = "Transaction type must be between 3 and 20 characters")
    String transactionType; // GIFT, SUBSCRIPTION

    @JsonProperty("item_id")
    @Positive(message = "Item ID must be greater than 0")
    int itemId; // gift_id or channel_id

    @Positive(message = "Quantity must be greater than 0")
    int quantity; // for gifts

    @JsonProperty("payment_method")
    @NotBlank(message = "Payment method is required")
    @Size(min = 3, max = 20, message = "Payment method must be between 3 and 20 characters")
    String paymentMethod; // VNPAY, STRIPE, PAYPAL

    @JsonProperty("return_url")
    @NotBlank(message = "Return URL is required")
    @Size(min = 5, max = 500, message = "Return URL must be between 5 and 500 characters")
    String returnUrl;
}
