package com.livestream.DTO.request.badge;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BadgeRequest {
    String name;
    String iconUrl;
    String description;
}
