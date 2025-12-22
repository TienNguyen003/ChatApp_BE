package com.livestream.DTO.request.badge;

import com.livestream.Entity.badge.BadgeCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BadgeRequest {
    @NotBlank
    @Size(max = 255)
    String name;

    @Size(max = 1024)
    String iconUrl;

    @Size(max = 5000)
    String description;

    @NotNull
    BadgeCategory category;
}
