package com.livestream.DTO.response.subscription;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.livestream.DTO.response.channel.ChannelResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionPackageResponse {
    int id;
    ChannelResponse channel;
    int tierLevel;
    String tierName;
    BigDecimal price;
    List<String> benefits;
    String description;
    boolean isActive;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
