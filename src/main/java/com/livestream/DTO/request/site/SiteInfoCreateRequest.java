package com.livestream.DTO.request.site;

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
public class SiteInfoCreateRequest {
    @NotBlank
    @Size(max = 255)
    String keyword;

    @NotBlank
    @Size(max = 10000)
    String content;

    @Positive
    Integer updatedBy;
}
