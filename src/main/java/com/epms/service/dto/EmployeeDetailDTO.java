package com.epms.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailDTO {
    private Long id;
    private String name;
    private String email;
    private DepartmentDTO department;
    private LocalDate dateOfJoining;
    private BigDecimal salary;
    private String managerName;
    private List<ProjectDTO> projects;
    private List<PerformanceReviewDTO> lastThreeReviews;
}