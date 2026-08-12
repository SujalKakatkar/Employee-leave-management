package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.LeaveApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveApprovalRepository extends JpaRepository<LeaveApproval, Integer> {
}
