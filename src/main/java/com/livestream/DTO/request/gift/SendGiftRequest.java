package com.livestream.DTO.request.gift;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendGiftRequest {
    int channelId;
    int giftId;
    int quantity;
}
