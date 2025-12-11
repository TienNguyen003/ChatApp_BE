package com.livestream.DTO.request.gift;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftRequest {
    String name;
    String iconUrl;
    BigDecimal price;
}
