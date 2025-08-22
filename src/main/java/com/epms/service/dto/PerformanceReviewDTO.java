package com.epms.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReviewDTO {
    private Long id;
    private LocalDate reviewDate;
    private BigDecimal score;
    private String comments;
}