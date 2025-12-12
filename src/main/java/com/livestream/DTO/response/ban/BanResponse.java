package com.livestream.DTO.response.ban;

import com.livestream.Entity.ban.BanType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BanResponse {
    int id;
    int channelId;
    String channelName;
    int userId;
    String username;
    int bannedById;
    String bannedByName;
    String reason;
    BanType banType;
    LocalDateTime expiresAt;
    LocalDateTime createdAt;
}
