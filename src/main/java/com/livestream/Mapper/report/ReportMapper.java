package com.livestream.Mapper.report;

import com.livestream.DTO.request.report.ReportRequest;
import com.livestream.DTO.response.report.ReportResponse;
import com.livestream.Entity.report.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "reporter", ignore = true)
    @Mapping(target = "targetUser", ignore = true)
    @Mapping(target = "livestream", ignore = true)
    @Mapping(target = "video", ignore = true)
    Report toReport(ReportRequest request);

    ReportResponse toReportResponse(Report report);
}
