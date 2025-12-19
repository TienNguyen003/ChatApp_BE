package com.livestream.DTO.request.gift;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendGiftRequest {
    @JsonProperty("channel_id")
    int channelId;

    @JsonProperty("gift_id")
    int giftId;

    int quantity;
}
