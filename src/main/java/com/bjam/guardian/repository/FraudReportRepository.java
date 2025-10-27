package com.bjam.guardian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bjam.guardian.model.FraudReport;
import com.bjam.guardian.model.User;

@Repository
public interface FraudReportRepository extends JpaRepository<FraudReport, Long> {
    List<FraudReport> findByReporter(User reporter);
    List<FraudReport> findByStatus(FraudReport.Status status);
}
