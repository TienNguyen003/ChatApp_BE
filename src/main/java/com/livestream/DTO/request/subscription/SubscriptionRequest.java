package com.livestream.DTO.request.subscription;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionRequest {
    @JsonProperty("channel_id")
    int channelId;

    String tier;

    BigDecimal price;
    
    int months;
}
