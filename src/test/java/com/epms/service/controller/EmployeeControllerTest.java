package com.epms.service.controller;

import com.epms.service.dto.EmployeeDTO;
import com.epms.service.dto.EmployeeDetailDTO;
import com.epms.service.exception.EmployeeNotFoundException;
import com.epms.service.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getEmployees_WithValidHeaders_ReturnsEmployees() throws Exception {
        // Given
        EmployeeDTO employee = new EmployeeDTO(1L, "John Doe", "john@company.com", 
            "Engineering", LocalDate.of(2020, 1, 15), BigDecimal.valueOf(75000), null);
        
        when(employeeService.getEmployeesWithFilters(any(), any(), any(), any()))
            .thenReturn(Arrays.asList(employee));

        // When & Then
        mockMvc.perform(get("/api/employees")
                .header("X-Client-Id", "epms-client-001")
                .header("X-Client-Secret", "secret123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john@company.com"));
    }

    @Test
    void getEmployees_WithFilters_ReturnsFilteredEmployees() throws Exception {
        // Given
        EmployeeDTO employee = new EmployeeDTO(1L, "John Doe", "john@company.com", 
            "Engineering", LocalDate.of(2020, 1, 15), BigDecimal.valueOf(75000), null);
        
        when(employeeService.getEmployeesWithFilters(eq(BigDecimal.valueOf(4.0)), 
            eq(LocalDate.of(2023, 12, 1)), eq(Arrays.asList(1L)), eq(Arrays.asList(1L))))
            .thenReturn(Arrays.asList(employee));

        // When & Then
        mockMvc.perform(get("/api/employees")
                .param("performanceScore", "4.0")
                .param("reviewDate", "2023-12-01")
                .param("departmentIds", "1")
                .param("projectIds", "1")
                .header("X-Client-Id", "epms-client-001")
                .header("X-Client-Secret", "secret123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void getEmployeeDetails_ValidId_ReturnsEmployeeDetails() throws Exception {
        // Given
        EmployeeDetailDTO employeeDetail = new EmployeeDetailDTO();
        employeeDetail.setId(1L);
        employeeDetail.setName("John Doe");
        employeeDetail.setEmail("john@company.com");
        
        when(employeeService.getEmployeeDetails(1L)).thenReturn(employeeDetail);

        // When & Then
        mockMvc.perform(get("/api/employees/1")
                .header("X-Client-Id", "epms-client-001")
                .header("X-Client-Secret", "secret123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@company.com"));
    }

    @Test
    void getEmployeeDetails_EmployeeNotFound_ReturnsNotFound() throws Exception {
        // Given
        when(employeeService.getEmployeeDetails(999L))
            .thenThrow(new EmployeeNotFoundException("Employee not found"));

        // When & Then
        mockMvc.perform(get("/api/employees/999")
                .header("X-Client-Id", "epms-client-001")
                .header("X-Client-Secret", "secret123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Employee Not Found"));
    }

    @Test
    void getEmployees_EmptyResult_ReturnsEmptyArray() throws Exception {
        // Given
        when(employeeService.getEmployeesWithFilters(any(), any(), any(), any()))
            .thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/employees")
                .header("X-Client-Id", "epms-client-001")
                .header("X-Client-Secret", "secret123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}