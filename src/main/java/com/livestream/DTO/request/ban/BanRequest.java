package com.livestream.DTO.request.ban;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.livestream.Entity.ban.BanType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BanRequest {
    @JsonProperty("user_id")
    int userId;

    String reason;

    @JsonProperty("ban_type")
    BanType banType;
    
    @JsonProperty("expires_at")
    LocalDateTime expiresAt; // null for PERMANENT
}
