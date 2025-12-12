package com.livestream.DTO.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
    @JsonProperty("transaction_type")
    String transactionType; // GIFT, SUBSCRIPTION

    @JsonProperty("item_id")
    int itemId; // gift_id or channel_id

    int quantity; // for gifts

    @JsonProperty("payment_method")
    String paymentMethod; // VNPAY, STRIPE, PAYPAL

    @JsonProperty("return_url")
    String returnUrl;
}
