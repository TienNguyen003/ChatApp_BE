package com.livestream.DTO.response.analytics;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalyticsResponse {
    LocalDate date;
    int totalViews;
    int totalFollowers;
    int totalSubscribers;
    double totalRevenue;
    int peakConcurrentViewers;
    int totalStreamTime;
    int totalGiftsReceived;
    double averageWatchTime;
}
