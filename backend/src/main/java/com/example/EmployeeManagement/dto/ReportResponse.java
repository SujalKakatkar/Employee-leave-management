package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportResponse {
    private Integer userId;
    private String name;

    private Integer managerId;

    private Role role;

    private double TotalLeaves;

    private double usedLeaves;
}
