package com.livestream.DTO.response.gift;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftResponse {
    int id;
    String name;
    String iconUrl;
    String color;
    BigDecimal price;
}
