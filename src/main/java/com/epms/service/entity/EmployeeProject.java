package com.epms.service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "employee_projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProject {
    
    @EmbeddedId
    private EmployeeProjectId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;
    
    @Column(name = "assigned_date")
    private LocalDate assignedDate;
    
    @Column(length = 100)
    private String role;
}