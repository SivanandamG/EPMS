package com.epms.service.repository;

import com.epms.service.entity.Department;
import com.epms.service.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Department testDepartment;
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        testDepartment = new Department();
        testDepartment.setName("Engineering");
        testDepartment.setBudget(BigDecimal.valueOf(1000000));
        testDepartment = entityManager.persistAndFlush(testDepartment);

        testEmployee = new Employee();
        testEmployee.setName("John Doe");
        testEmployee.setEmail("john.doe@company.com");
        testEmployee.setDepartment(testDepartment);
        testEmployee.setDateOfJoining(LocalDate.of(2020, 1, 15));
        testEmployee.setSalary(BigDecimal.valueOf(75000));
        testEmployee = entityManager.persistAndFlush(testEmployee);
    }

    @Test
    void findEmployeesWithFilters_NullFilters_ReturnsAllEmployees() {
        // When
        List<Employee> result = employeeRepository.findEmployeesWithFilters(
            null, null, null, null);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(e -> e.getName().equals("John Doe")));
    }

    @Test
    void findEmployeesWithFilters_DepartmentFilter_ReturnsFilteredEmployees() {
        // When
        List<Employee> result = employeeRepository.findEmployeesWithFilters(
            null, null, Arrays.asList(testDepartment.getId()), null);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(e -> 
            e.getDepartment().getId().equals(testDepartment.getId())));
    }

    @Test
    void findByIdWithDetails_ValidId_ReturnsEmployeeWithDetails() {
        // When
        Employee result = employeeRepository.findByIdWithDetails(testEmployee.getId());

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertNotNull(result.getDepartment());
        assertEquals("Engineering", result.getDepartment().getName());
    }

    @Test
    void findByIdWithDetails_InvalidId_ReturnsNull() {
        // When
        Employee result = employeeRepository.findByIdWithDetails(999L);

        // Then
        assertNull(result);
    }
}