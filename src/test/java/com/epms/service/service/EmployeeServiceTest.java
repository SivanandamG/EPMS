package com.epms.service.service;

import com.epms.service.dto.EmployeeDTO;
import com.epms.service.dto.EmployeeDetailDTO;
import com.epms.service.entity.Department;
import com.epms.service.entity.Employee;
import com.epms.service.entity.PerformanceReview;
import com.epms.service.exception.EmployeeNotFoundException;
import com.epms.service.exception.InvalidFilterException;
import com.epms.service.repository.EmployeeRepository;
import com.epms.service.repository.PerformanceReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PerformanceReviewRepository performanceReviewRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee testEmployee;
    private Department testDepartment;
    private PerformanceReview testReview;

    @BeforeEach
    void setUp() {
        testDepartment = new Department();
        testDepartment.setId(1L);
        testDepartment.setName("Engineering");
        testDepartment.setBudget(BigDecimal.valueOf(1000000));

        testEmployee = new Employee();
        testEmployee.setId(1L);
        testEmployee.setName("John Doe");
        testEmployee.setEmail("john.doe@company.com");
        testEmployee.setDepartment(testDepartment);
        testEmployee.setDateOfJoining(LocalDate.of(2020, 1, 15));
        testEmployee.setSalary(BigDecimal.valueOf(75000));
        testEmployee.setEmployeeProjects(Collections.emptyList());
        testEmployee.setPerformanceReviews(Collections.emptyList());

        testReview = new PerformanceReview();
        testReview.setId(1L);
        testReview.setEmployee(testEmployee);
        testReview.setReviewDate(LocalDate.of(2023, 12, 1));
        testReview.setScore(BigDecimal.valueOf(4.5));
        testReview.setComments("Excellent performance");
    }

    @Test
    void getEmployeesWithFilters_ValidParameters_ReturnsEmployees() {
        // Given
        List<Employee> employees = Arrays.asList(testEmployee);
        when(employeeRepository.findEmployeesWithFilters(any(), any(), any(), any()))
            .thenReturn(employees);

        // When
        List<EmployeeDTO> result = employeeService.getEmployeesWithFilters(
            BigDecimal.valueOf(4.0), LocalDate.of(2023, 12, 1), 
            Arrays.asList(1L), Arrays.asList(1L));

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(employeeRepository).findEmployeesWithFilters(any(), any(), any(), any());
    }

    @Test
    void getEmployeesWithFilters_InvalidPerformanceScore_ThrowsException() {
        // When & Then
        assertThrows(InvalidFilterException.class, () -> 
            employeeService.getEmployeesWithFilters(
                BigDecimal.valueOf(6.0), null, null, null));
    }

    @Test
    void getEmployeesWithFilters_InvalidDepartmentId_ThrowsException() {
        // When & Then
        assertThrows(InvalidFilterException.class, () -> 
            employeeService.getEmployeesWithFilters(
                null, null, Arrays.asList(-1L), null));
    }

    @Test
    void getEmployeeDetails_ValidId_ReturnsEmployeeDetail() {
        // Given
        when(employeeRepository.findByIdWithDetails(1L)).thenReturn(testEmployee);
        when(performanceReviewRepository.findTop3ByEmployeeIdOrderByReviewDateDesc(1L))
            .thenReturn(Arrays.asList(testReview));

        // When
        EmployeeDetailDTO result = employeeService.getEmployeeDetails(1L);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("Engineering", result.getDepartment().getName());
        verify(employeeRepository).findByIdWithDetails(1L);
    }

    @Test
    void getEmployeeDetails_InvalidId_ThrowsException() {
        // When & Then
        assertThrows(InvalidFilterException.class, () -> 
            employeeService.getEmployeeDetails(-1L));
    }

    @Test
    void getEmployeeDetails_EmployeeNotFound_ThrowsException() {
        // Given
        when(employeeRepository.findByIdWithDetails(999L)).thenReturn(null);

        // When & Then
        assertThrows(EmployeeNotFoundException.class, () -> 
            employeeService.getEmployeeDetails(999L));
    }

    @Test
    void getEmployeesWithFilters_EmptyResult_ReturnsEmptyList() {
        // Given
        when(employeeRepository.findEmployeesWithFilters(any(), any(), any(), any()))
            .thenReturn(Collections.emptyList());

        // When
        List<EmployeeDTO> result = employeeService.getEmployeesWithFilters(
            null, null, null, null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}