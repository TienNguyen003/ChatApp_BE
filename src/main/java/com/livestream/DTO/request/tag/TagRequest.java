package com.livestream.DTO.request.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagRequest {
    @NotBlank
    @Size(max = 100)
    String name;

    @Size(max = 1000)
    String description;
}
