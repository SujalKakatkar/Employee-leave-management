package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.LeaveRequest;
import com.example.EmployeeManagement.enums.LeaveStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

//    @EntityGraph(attributePaths = {"leaveType", })
    List<LeaveRequest> findAllByEmployee_UserId(Integer id);

    Long countByEmployee_UserIdAndStatus(Integer userId, LeaveStatus leaveStatus);

}
