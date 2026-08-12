package com.example.EmployeeManagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "leave_types")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaveTypeId;

    @Column(unique = true, nullable = false)
    private String name;

    private Integer defaultDaysPerYear;

    private Boolean isPaid;


    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
