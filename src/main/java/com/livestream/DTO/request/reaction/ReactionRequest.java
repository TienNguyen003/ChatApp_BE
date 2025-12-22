package com.livestream.DTO.request.reaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactionRequest {
    @JsonProperty("livestream_id")
    @Positive
    int livestreamId;

    @NotBlank
    @Size(max = 50)
    String type;
}
