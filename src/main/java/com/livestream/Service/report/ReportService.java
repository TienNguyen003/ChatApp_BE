package com.livestream.Service.report;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.report.ReportRequest;
import com.livestream.DTO.response.report.ReportResponse;
import com.livestream.Entity.livestream.Livestream;
import com.livestream.Entity.report.Report;
import com.livestream.Entity.user.Users;
import com.livestream.Entity.video.Video;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.report.ReportMapper;
import com.livestream.Repository.livestream.LivestreamRepository;
import com.livestream.Repository.report.ReportRepository;
import com.livestream.Repository.user.UserRepository;
import com.livestream.Repository.video.VideoRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportService {
    ReportRepository reportRepository;
    UserRepository userRepository;
    LivestreamRepository livestreamRepository;
    VideoRepository videoRepository;
    ReportMapper reportMapper;

    public ReportResponse createReport(ReportRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users reporter = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Report report = reportMapper.toReport(request);
        report.setReporter(reporter);
        report.setStatus("PENDING");
        report.setCreatedAt(LocalDateTime.now());

        if (request.getTargetUserId() != null) {
            Users targetUser = userRepository.findById(request.getTargetUserId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            report.setTargetUser(targetUser);
        }

        if (request.getLivestreamId() != null) {
            Livestream livestream = livestreamRepository.findById(request.getLivestreamId())
                    .orElseThrow(() -> new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED));
            report.setLivestream(livestream);
        }

        if (request.getVideoId() != null) {
            Video video = videoRepository.findById(request.getVideoId())
                    .orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_EXISTED));
            report.setVideo(video);
        }

        return reportMapper.toReportResponse(reportRepository.save(report));
    }

    public Page<ReportResponse> getAllReports(Pageable pageable) {
        return reportRepository.findAll(pageable)
                .map(reportMapper::toReportResponse);
    }

    public Page<ReportResponse> getReportsByStatus(String status, Pageable pageable) {
        return reportRepository.findByStatus(status, pageable)
                .map(reportMapper::toReportResponse);
    }

    public ReportResponse updateReportStatus(int id, String status) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_EXISTED));

        report.setStatus(status);
        return reportMapper.toReportResponse(reportRepository.save(report));
    }

    public void deleteReport(int id) {
        if (!reportRepository.existsById(id)) {
            throw new AppException(ErrorCode.REPORT_NOT_EXISTED);
        }
        reportRepository.deleteById(id);
    }
}
