package com.example.EmployeeManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaveTypeCreateRequest {
    private String name;

    private Integer defaultDaysPerYear;

    private Boolean isPaid;


}
