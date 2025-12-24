package com.livestream.DTO.request.clip;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClipCreationRequest {
    @JsonProperty("livestream_id")
    @Positive(message = "Livestream ID must be greater than 0")
    int livestreamId;

    @NotBlank(message = "Clip title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    String title;

    @JsonProperty("start_time")
    @NotBlank(message = "Start time is required")
    @Size(min = 1, max = 50, message = "Start time must be between 1 and 50 characters")
    String startTime;

    @JsonProperty("end_time")
    @NotBlank(message = "End time is required")
    @Size(min = 1, max = 50, message = "End time must be between 1 and 50 characters")
    String endTime;

    @JsonProperty("video_url")
    @NotBlank(message = "Video URL is required")
    @Size(min = 5, max = 500, message = "Video URL must be between 5 and 500 characters")
    String videoUrl;

    @JsonProperty("thumbnail_url")
    @Size(max = 500, message = "Thumbnail URL must not exceed 500 characters")
    String thumbnailUrl;

    @JsonProperty("view")
    @PositiveOrZero(message = "View must be greater than or equal to 0")
    int view;
}
