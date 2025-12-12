package com.livestream.Service.analytics;

import com.livestream.DTO.response.analytics.AnalyticsResponse;
import com.livestream.DTO.response.analytics.DashboardResponse;
import com.livestream.Entity.analytics.ChannelAnalytics;
import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Repository.analytics.ChannelAnalyticsRepository;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnalyticsService {
    ChannelAnalyticsRepository analyticsRepository;
    ChannelRepository channelRepository;
    UserRepository userRepository;

    public DashboardResponse getMyDashboard() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Channel channel = channelRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        return buildDashboard(channel.getId());
    }

    public DashboardResponse getChannelDashboard(int channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new AppException(ErrorCode.CHANNEL_NOT_EXISTED);
        }
        return buildDashboard(channelId);
    }

    public List<AnalyticsResponse> getAnalytics(int channelId, LocalDate startDate, LocalDate endDate) {
        if (!channelRepository.existsById(channelId)) {
            throw new AppException(ErrorCode.CHANNEL_NOT_EXISTED);
        }

        return analyticsRepository.findByChannelIdAndDateBetweenOrderByDateAsc(channelId, startDate, endDate)
                .stream()
                .map(this::toAnalyticsResponse)
                .collect(Collectors.toList());
    }

    private DashboardResponse buildDashboard(int channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate lastMonthStart = monthStart.minusMonths(1);
        LocalDate lastMonthEnd = monthStart.minusDays(1);

        // Get today's analytics
        ChannelAnalytics todayAnalytics = analyticsRepository
                .findByChannelIdAndDate(channelId, today)
                .orElse(createEmptyAnalytics());

        // Get this month's analytics
        List<ChannelAnalytics> monthAnalytics = analyticsRepository
                .findByChannelIdAndDateBetweenOrderByDateAsc(channelId, monthStart, today);

        // Get last month's analytics for growth rate
        List<ChannelAnalytics> lastMonthAnalytics = analyticsRepository
                .findByChannelIdAndDateBetweenOrderByDateAsc(channelId, lastMonthStart, lastMonthEnd);

        // Calculate totals
        Double totalRevenue = analyticsRepository.getTotalRevenueByChannelId(channelId);
        Integer totalViews = analyticsRepository.getTotalViewsByChannelId(channelId);

        int monthViews = monthAnalytics.stream().mapToInt(ChannelAnalytics::getTotalViews).sum();
        int monthFollowers = monthAnalytics.stream().mapToInt(ChannelAnalytics::getTotalFollowers).sum();
        int monthSubscribers = monthAnalytics.stream().mapToInt(ChannelAnalytics::getTotalSubscribers).sum();
        double monthRevenue = monthAnalytics.stream().mapToDouble(ChannelAnalytics::getTotalRevenue).sum();

        int lastMonthViews = lastMonthAnalytics.stream().mapToInt(ChannelAnalytics::getTotalViews).sum();
        double lastMonthRevenue = lastMonthAnalytics.stream().mapToDouble(ChannelAnalytics::getTotalRevenue).sum();

        // Calculate growth rates
        double viewsGrowthRate = calculateGrowthRate(monthViews, lastMonthViews);
        double revenueGrowthRate = calculateGrowthRate(monthRevenue, lastMonthRevenue);

        return DashboardResponse.builder()
                .totalFollowers(channel.getFollowersCount())
                .totalSubscribers(0) // Would get from subscription count
                .totalViews(totalViews != null ? totalViews : 0)
                .totalRevenue(totalRevenue != null ? totalRevenue : 0.0)
                .todayViews(todayAnalytics.getTotalViews())
                .todayFollowers(todayAnalytics.getTotalFollowers())
                .todaySubscribers(todayAnalytics.getTotalSubscribers())
                .todayRevenue(todayAnalytics.getTotalRevenue())
                .monthViews(monthViews)
                .monthFollowers(monthFollowers)
                .monthSubscribers(monthSubscribers)
                .monthRevenue(monthRevenue)
                .viewsGrowthRate(viewsGrowthRate)
                .followersGrowthRate(0.0)
                .revenueGrowthRate(revenueGrowthRate)
                .topVideo("N/A")
                .topLivestream("N/A")
                .build();
    }

    private double calculateGrowthRate(double current, double previous) {
        if (previous == 0)
            return 0.0;
        return ((current - previous) / previous) * 100;
    }

    private ChannelAnalytics createEmptyAnalytics() {
        return ChannelAnalytics.builder()
                .totalViews(0)
                .totalFollowers(0)
                .totalSubscribers(0)
                .totalRevenue(0.0)
                .peakConcurrentViewers(0)
                .totalStreamTime(0)
                .totalGiftsReceived(0)
                .averageWatchTime(0.0)
                .build();
    }

    private AnalyticsResponse toAnalyticsResponse(ChannelAnalytics analytics) {
        return AnalyticsResponse.builder()
                .date(analytics.getDate())
                .totalViews(analytics.getTotalViews())
                .totalFollowers(analytics.getTotalFollowers())
                .totalSubscribers(analytics.getTotalSubscribers())
                .totalRevenue(analytics.getTotalRevenue())
                .peakConcurrentViewers(analytics.getPeakConcurrentViewers())
                .totalStreamTime(analytics.getTotalStreamTime())
                .totalGiftsReceived(analytics.getTotalGiftsReceived())
                .averageWatchTime(analytics.getAverageWatchTime())
                .build();
    }
}
