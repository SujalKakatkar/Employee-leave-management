package com.example.EmployeeManagement.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HolidayResponse {

    private Integer holidayId;

    private LocalDate date;

    private String name;
}
