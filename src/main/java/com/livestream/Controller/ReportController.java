package com.livestream.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestream.DTO.request.report.ReportRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.report.ReportResponse;
import com.livestream.Service.report.ReportService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}reports")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportController {
    ReportService reportService;

    @PostMapping
    ApiResponse<ReportResponse> createReport(@RequestBody ReportRequest request) {
        return ApiResponse.<ReportResponse>builder()
                .result(reportService.createReport(request))
                .build();
    }

    @GetMapping("/list")
    ApiResponse<List<ReportResponse>> getAllReports(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<ReportResponse> pageData = reportService.getAllReports(pageable);
        return ApiResponse.<List<ReportResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/status/{status}")
    ApiResponse<Page<ReportResponse>> getReportsByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<Page<ReportResponse>>builder()
                .result(reportService.getReportsByStatus(status, pageable))
                .build();
    }

    @PutMapping("/{id}/status")
    ApiResponse<ReportResponse> updateReportStatus(
            @PathVariable int id,
            @RequestParam String status) {
        return ApiResponse.<ReportResponse>builder()
                .result(reportService.updateReportStatus(id, status))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteReport(@PathVariable int id) {
        reportService.deleteReport(id);
        return ApiResponse.<Void>builder().build();
    }
}
