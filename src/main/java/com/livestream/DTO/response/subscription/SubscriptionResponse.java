package com.livestream.DTO.response.subscription;

import com.livestream.DTO.response.channel.ChannelResponse;
import com.livestream.DTO.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionResponse {
    int id;
    UserResponse user;
    ChannelResponse channel;
    String tier;
    BigDecimal price;
    LocalDateTime startedAt;
    int months;
}
