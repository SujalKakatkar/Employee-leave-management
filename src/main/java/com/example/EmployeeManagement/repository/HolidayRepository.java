package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {

    Optional<Holiday> findByName(String name);
}
