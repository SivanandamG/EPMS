package com.epms.service.repository;

import com.epms.service.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
    
    @Query("SELECT pr FROM PerformanceReview pr " +
           "WHERE pr.employee.id = :employeeId " +
           "ORDER BY pr.reviewDate DESC")
    List<PerformanceReview> findTop3ByEmployeeIdOrderByReviewDateDesc(@Param("employeeId") Long employeeId);
}