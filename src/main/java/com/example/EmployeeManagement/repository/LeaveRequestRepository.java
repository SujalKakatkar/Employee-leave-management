package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.LeaveRequest;
import com.example.EmployeeManagement.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    List<LeaveRequest> findAllByEmployee_UserId(Integer id);

}
