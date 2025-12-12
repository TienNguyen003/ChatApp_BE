package com.livestream.DTO.response.analytics;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardResponse {
    // Overview stats
    int totalFollowers;
    int totalSubscribers;
    int totalViews;
    double totalRevenue;

    // Today's stats
    int todayViews;
    int todayFollowers;
    int todaySubscribers;
    double todayRevenue;

    // This month stats
    int monthViews;
    int monthFollowers;
    int monthSubscribers;
    double monthRevenue;

    // Growth rates
    double viewsGrowthRate;
    double followersGrowthRate;
    double revenueGrowthRate;

    // Top content
    String topVideo;
    String topLivestream;
}
