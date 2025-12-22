package com.livestream.DTO.request.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportRequest {
    @JsonProperty("target_user_id")
    @Positive(message = "Target user ID must be greater than 0")
    Integer targetUserId;

    @JsonProperty("livestream_id")
    @Positive(message = "Livestream ID must be greater than 0")
    Integer livestreamId;

    @JsonProperty("video_id")
    @Positive(message = "Video ID must be greater than 0")
    Integer videoId;

    @NotBlank(message = "Reason is required")
    @Size(min = 5, max = 500, message = "Reason must be between 5 and 500 characters")
    String reason;
}
