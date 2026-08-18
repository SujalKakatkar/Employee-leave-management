package com.example.EmployeeManagement.dto;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HolidayCreateRequest {

    @NotBlank(message = "the holiday date is required")
    @FutureOrPresent(message = "you can't set a holiday in the past")
    private LocalDate date;

    @NotBlank(message = "the name of the holiday is required")
    private String name;
}
