package com.livestream.DTO.response.gift;

import com.livestream.DTO.response.channel.ChannelResponse;
import com.livestream.DTO.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserGiftResponse {
    int id;
    UserResponse user;
    ChannelResponse channel;
    GiftResponse gift;
    int quantity;
    LocalDateTime sentAt;
}
