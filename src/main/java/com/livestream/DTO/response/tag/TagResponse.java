package com.livestream.DTO.response.tag;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagResponse {
    int id;
    String name;
    String description;
    int usageCount;
    LocalDateTime createdAt;
}
