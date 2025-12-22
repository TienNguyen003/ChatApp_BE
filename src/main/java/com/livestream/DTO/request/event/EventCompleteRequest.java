package com.livestream.DTO.request.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventCompleteRequest {
    @PositiveOrZero(message = "Score must be zero or greater")
    Integer score;

    @Size(max = 200, message = "Result must not exceed 200 characters")
    String result;
}
