package com.epms.service.repository;

import com.epms.service.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    @Query("SELECT DISTINCT e FROM Employee e " +
           "LEFT JOIN e.performanceReviews pr " +
           "LEFT JOIN e.employeeProjects ep " +
           "LEFT JOIN ep.project p " +
           "WHERE (:score IS NULL OR pr.score >= :score) " +
           "AND (:reviewDate IS NULL OR pr.reviewDate = :reviewDate) " +
           "AND (:departmentIds IS NULL OR e.department.id IN :departmentIds) " +
           "AND (:projectIds IS NULL OR p.id IN :projectIds)")
    Page<Employee> findEmployeesWithFilters(
        @Param("score") BigDecimal score,
        @Param("reviewDate") LocalDate reviewDate,
        @Param("departmentIds") List<Long> departmentIds,
        @Param("projectIds") List<Long> projectIds,
        Pageable pageable
    );
    
    @Query("SELECT e FROM Employee e " +
           "LEFT JOIN FETCH e.department " +
           "LEFT JOIN FETCH e.employeeProjects ep " +
           "LEFT JOIN FETCH ep.project " +
           "WHERE e.id = :id")
    Employee findByIdWithDetails(@Param("id") Long id);
}