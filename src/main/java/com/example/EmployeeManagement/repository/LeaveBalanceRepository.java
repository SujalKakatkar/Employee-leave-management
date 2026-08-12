package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer> {

    Optional<LeaveBalance> findByUser_UserIdAndLeaveType_LeaveTypeId(Integer userId, Integer leaveTypeId);

    List<LeaveBalance> findByUser_Username(String username);
}
