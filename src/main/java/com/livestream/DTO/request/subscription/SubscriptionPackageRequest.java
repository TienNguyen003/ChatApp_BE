package com.livestream.DTO.request.subscription;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionPackageRequest {
    @Positive
    int tierLevel;

    @NotBlank
    String tierName;

    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal price;

    List<String> benefits;

    String description;
}
