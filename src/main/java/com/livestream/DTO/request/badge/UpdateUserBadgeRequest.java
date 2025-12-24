package com.livestream.DTO.request.badge;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserBadgeRequest {
    @JsonProperty("user_badge_id")
    int userBadgeId;

    // Increment progress by this value (optional)
    @Min(0)
    Integer deltaValue;

    // Set progress to this value (optional)
    @Min(0)
    Integer setValue;

    // Explicitly toggle completion state (optional)
    Boolean isCompleted;
}