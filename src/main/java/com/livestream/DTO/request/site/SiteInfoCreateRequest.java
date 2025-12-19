package com.livestream.DTO.request.site;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SiteInfoCreateRequest {
    String keyword;
    String content;
    Integer updatedBy;
}
