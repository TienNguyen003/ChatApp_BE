package com.livestream.Repository.report;

import com.livestream.Entity.report.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    Page<Report> findByReporterId(int reporterId, Pageable pageable);

    Page<Report> findByStatus(String status, Pageable pageable);
}
