package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.enums.LeaveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaveRequestResponse {
    private Integer requestId;


    private LocalDate startDate;

    private LocalDate endDate;

    private Double numberOfDays;

    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;

}
