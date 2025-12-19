package com.livestream.DTO.response.site;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SiteInfoResponse {
    String keyword;
    String content;
    LocalDateTime updatedAt;
}
