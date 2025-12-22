package com.livestream.DTO.request.ban;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.livestream.Entity.ban.BanType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @Positive
    int userId;

    @Size(max = 500)
    String reason;

    @JsonProperty("ban_type")
    @NotNull
    BanType banType;

    @JsonProperty("expires_at")
    @Future(message = "expiresAt must be in the future")
    LocalDateTime expiresAt; // null for PERMANENT
}
