package com.livestream.DTO.response.wallet;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopUpPackageResponse {
    Long id;
    String name;
    String description;
    BigDecimal amount;
    BigDecimal bonusAmount;
    BigDecimal totalAmount;
    String currency;
    Boolean isActive;
    Boolean isPopular;
    Integer displayOrder;
    String icon;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
