package com.epms.service.controller;

import com.epms.service.aop.SecureApi;
import com.epms.service.dto.EmployeeDTO;
import com.epms.service.dto.EmployeeDetailDTO;
import com.epms.service.dto.PagedEmployeeResponse;
import com.epms.service.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Validated
@Slf4j
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
    @GetMapping
    @SecureApi
    public ResponseEntity<PagedEmployeeResponse> getEmployees(
            @RequestParam(required = false) BigDecimal performanceScore,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reviewDate,
            @RequestParam(required = false) List<Long> departmentIds,
            @RequestParam(required = false) List<Long> projectIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        
        log.info("GET /api/employees - Filters: score={}, date={}, depts={}, projects={}, page={}, size={}", 
                performanceScore, reviewDate, departmentIds, projectIds, page, size);
        
        PagedEmployeeResponse response = employeeService.getEmployeesWithFilters(
            performanceScore, reviewDate, departmentIds, projectIds, page, size);
        
        log.info("Returning {} employees (page {} of {})", response.getEmployees().size(), 
                response.getCurrentPage() + 1, response.getTotalPages());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @SecureApi
    public ResponseEntity<EmployeeDetailDTO> getEmployeeDetails(@PathVariable Long id) {
        log.info("GET /api/employees/{} - Fetching employee details", id);
        
        EmployeeDetailDTO employee = employeeService.getEmployeeDetails(id);
        
        log.info("Successfully retrieved details for employee: {}", employee.getName());
        return ResponseEntity.ok(employee);
    }
}