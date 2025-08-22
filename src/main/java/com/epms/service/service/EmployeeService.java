package com.epms.service.service;

import com.epms.service.dto.*;
import com.epms.service.entity.Employee;
import com.epms.service.entity.EmployeeProject;
import com.epms.service.entity.PerformanceReview;
import com.epms.service.exception.EmployeeNotFoundException;
import com.epms.service.exception.InvalidFilterException;
import com.epms.service.repository.EmployeeRepository;
import com.epms.service.repository.PerformanceReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    private final PerformanceReviewRepository performanceReviewRepository;
    
    public PagedEmployeeResponse getEmployeesWithFilters(BigDecimal performanceScore, 
                                                        LocalDate reviewDate,
                                                        List<Long> departmentIds, 
                                                        List<Long> projectIds,
                                                        int page,
                                                        int size) {
        try {
            validateFilterParameters(performanceScore, departmentIds, projectIds);
            validatePaginationParameters(page, size);
            
            log.info("Fetching employees with filters - score: {}, date: {}, deptIds: {}, projIds: {}, page: {}, size: {}", 
                    performanceScore, reviewDate, departmentIds, projectIds, page, size);
            
            Pageable pageable = PageRequest.of(page, size);
            Page<Employee> employeePage = employeeRepository.findEmployeesWithFilters(
                performanceScore, reviewDate, departmentIds, projectIds, pageable);
            
            List<EmployeeDTO> employeeDTOs = employeePage.getContent().stream()
                .map(this::convertToEmployeeDTO)
                .collect(Collectors.toList());
            
            return new PagedEmployeeResponse(
                employeeDTOs,
                employeePage.getNumber(),
                employeePage.getTotalPages(),
                employeePage.getTotalElements(),
                employeePage.getSize(),
                employeePage.hasNext(),
                employeePage.hasPrevious()
            );
                
        } catch (Exception e) {
            log.error("Error fetching employees with filters", e);
            throw e;
        }
    }
    
    private void validateFilterParameters(BigDecimal performanceScore, List<Long> departmentIds, List<Long> projectIds) {
        if (performanceScore != null && (performanceScore.compareTo(BigDecimal.ZERO) < 0 || performanceScore.compareTo(BigDecimal.valueOf(5)) > 0)) {
            throw new InvalidFilterException("Performance score must be between 0 and 5");
        }
        
        if (departmentIds != null && departmentIds.stream().anyMatch(id -> id <= 0)) {
            throw new InvalidFilterException("Department IDs must be positive numbers");
        }
        
        if (projectIds != null && projectIds.stream().anyMatch(id -> id <= 0)) {
            throw new InvalidFilterException("Project IDs must be positive numbers");
        }
    }
    
    private void validatePaginationParameters(int page, int size) {
        if (page < 0) {
            throw new InvalidFilterException("Page number must be non-negative");
        }
        if (size <= 0 || size > 100) {
            throw new InvalidFilterException("Page size must be between 1 and 100");
        }
    }
    
    public EmployeeDetailDTO getEmployeeDetails(Long employeeId) {
        try {
            if (employeeId == null || employeeId <= 0) {
                throw new InvalidFilterException("Employee ID must be a positive number");
            }
            
            log.info("Fetching employee details for ID: {}", employeeId);
            
            Employee employee = Optional.ofNullable(employeeRepository.findByIdWithDetails(employeeId))
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
            
            List<PerformanceReview> lastThreeReviews = performanceReviewRepository
                .findTop3ByEmployeeIdOrderByReviewDateDesc(employeeId)
                .stream()
                .limit(3)
                .collect(Collectors.toList());
            
            log.info("Successfully fetched details for employee: {}", employee.getName());
            return convertToEmployeeDetailDTO(employee, lastThreeReviews);
            
        } catch (EmployeeNotFoundException | InvalidFilterException e) {
            log.warn("Employee lookup failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error fetching employee details for ID: {}", employeeId, e);
            throw new RuntimeException("Failed to fetch employee details", e);
        }
    }
    
    private EmployeeDTO convertToEmployeeDTO(Employee employee) {
        return new EmployeeDTO(
            employee.getId(),
            employee.getName(),
            employee.getEmail(),
            employee.getDepartment() != null ? employee.getDepartment().getName() : null,
            employee.getDateOfJoining(),
            employee.getSalary(),
            employee.getManager() != null ? employee.getManager().getName() : null
        );
    }
    
    private EmployeeDetailDTO convertToEmployeeDetailDTO(Employee employee, List<PerformanceReview> reviews) {
        DepartmentDTO departmentDTO = null;
        if (employee.getDepartment() != null) {
            departmentDTO = new DepartmentDTO(
                employee.getDepartment().getId(),
                employee.getDepartment().getName(),
                employee.getDepartment().getBudget()
            );
        }
        
        List<ProjectDTO> projectDTOs = Optional.ofNullable(employee.getEmployeeProjects())
            .orElse(Collections.emptyList())
            .stream()
            .map(this::convertToProjectDTO)
            .collect(Collectors.toList());
        
        List<PerformanceReviewDTO> reviewDTOs = reviews.stream()
            .map(this::convertToPerformanceReviewDTO)
            .collect(Collectors.toList());
        
        return new EmployeeDetailDTO(
            employee.getId(),
            employee.getName(),
            employee.getEmail(),
            departmentDTO,
            employee.getDateOfJoining(),
            employee.getSalary(),
            employee.getManager() != null ? employee.getManager().getName() : null,
            projectDTOs,
            reviewDTOs
        );
    }
    
    private ProjectDTO convertToProjectDTO(EmployeeProject employeeProject) {
        return new ProjectDTO(
            employeeProject.getProject().getId(),
            employeeProject.getProject().getName(),
            employeeProject.getProject().getStartDate(),
            employeeProject.getProject().getEndDate(),
            employeeProject.getRole(),
            employeeProject.getAssignedDate()
        );
    }
    
    private PerformanceReviewDTO convertToPerformanceReviewDTO(PerformanceReview review) {
        return new PerformanceReviewDTO(
            review.getId(),
            review.getReviewDate(),
            review.getScore(),
            review.getComments()
        );
    }
}