package com.livestream.Repository.analytics;

import com.livestream.Entity.analytics.ChannelAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelAnalyticsRepository extends JpaRepository<ChannelAnalytics, Integer> {
    Optional<ChannelAnalytics> findByChannelIdAndDate(int channelId, LocalDate date);

    List<ChannelAnalytics> findByChannelIdAndDateBetweenOrderByDateAsc(
            int channelId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(ca.totalRevenue) FROM ChannelAnalytics ca WHERE ca.channel.id = :channelId")
    Double getTotalRevenueByChannelId(@Param("channelId") int channelId);

    @Query("SELECT SUM(ca.totalViews) FROM ChannelAnalytics ca WHERE ca.channel.id = :channelId")
    Integer getTotalViewsByChannelId(@Param("channelId") int channelId);
}
