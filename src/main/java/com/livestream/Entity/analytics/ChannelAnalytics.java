package com.livestream.Entity.analytics;

import com.livestream.Entity.channel.Channel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "channel_analytics")
public class ChannelAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    Channel channel;

    LocalDate date;

    int totalViews;

    int totalFollowers;

    int totalSubscribers;

    double totalRevenue;

    int peakConcurrentViewers;

    int totalStreamTime; // in minutes

    int totalGiftsReceived;

    double averageWatchTime; // in minutes
}
