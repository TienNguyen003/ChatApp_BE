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

    @NotNull(message = "Amount is required")
    @PositiveOrZero(message = "Amount must be positive or zero")
    BigDecimal amount;

    @NotNull(message = "Bonus amount is required")
    @PositiveOrZero(message = "Bonus amount must be positive or zero")
    BigDecimal bonusAmount;

    @NotBlank(message = "Currency is required")
    String currency;

    Boolean isActive;

    Integer displayOrder;

    String icon;
}
