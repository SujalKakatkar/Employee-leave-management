package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {

}
