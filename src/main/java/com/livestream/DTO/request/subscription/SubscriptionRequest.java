package com.livestream.DTO.request.subscription;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionRequest {
    @JsonProperty("channel_id")
    @Positive
    int channelId;

    @NotBlank
    String tier;

    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal price;

    @Positive
    int months;
}
