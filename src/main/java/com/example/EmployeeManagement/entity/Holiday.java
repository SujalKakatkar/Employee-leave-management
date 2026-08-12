package com.example.EmployeeManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "holidays")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer holidayId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String name;
}
