package com.livestream.DTO.request.gift;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftRequest {
    @NotBlank
    @Size(max = 255)
    String name;

    @Size(max = 1024)
    String iconUrl;

    @Size(max = 50)
    String color;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal price;
}
