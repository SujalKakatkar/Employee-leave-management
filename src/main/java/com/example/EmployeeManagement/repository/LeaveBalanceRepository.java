package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer> {

    Optional<LeaveBalance> findByUser_UserIdAndLeaveType_LeaveTypeIdAndYear(Integer userId, Integer leaveTypeId,Integer year);

    List<LeaveBalance> findAllByUser_UserId(Integer userId);
    boolean existsByUser_UserIdAndUser_EnabledTrue(Integer userId);
    boolean existsByUser_userIdAndUser_EnabledTrueAndYear(Integer userId, Integer Year);

}
