package com.livestream.DTO.request.badge;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignBadgeRequest {
    @JsonProperty("user_id")
    int userId;

    @JsonProperty("badge_id")
    int badgeId;
}
