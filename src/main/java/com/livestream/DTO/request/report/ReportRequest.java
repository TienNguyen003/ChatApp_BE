package com.livestream.DTO.request.report;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportRequest {
    Integer targetUserId;
    Integer livestreamId;
    Integer videoId;
    String reason;
}
