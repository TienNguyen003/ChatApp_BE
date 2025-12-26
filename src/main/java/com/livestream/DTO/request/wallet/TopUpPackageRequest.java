package com.livestream.DTO.request.wallet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopUpPackageRequest {
    @NotBlank(message = "Package name is required")
    String name;

    @NotBlank(message = "Description is required")
    String description;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be positive or zero")
    BigDecimal price;

    @NotNull(message = "Base coins is required")
    @PositiveOrZero(message = "Base coins must be positive or zero")
    BigDecimal baseCoins;

    @NotNull(message = "Bonus coins is required")
    @PositiveOrZero(message = "Bonus coins must be positive or zero")
    BigDecimal bonusCoins;

    @NotBlank(message = "Currency is required")
    String currency;

    Boolean isActive;

    Boolean isPopular;

    Integer displayOrder;

    String icon;
}
