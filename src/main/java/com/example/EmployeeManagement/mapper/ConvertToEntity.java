package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.LeaveApprovalDto;
import com.example.EmployeeManagement.dto.LeaveRequestDto;
import com.example.EmployeeManagement.dto.user.UserRequest;
import com.example.EmployeeManagement.entity.*;
import com.example.EmployeeManagement.enums.ApproverRole;
import com.example.EmployeeManagement.enums.LeaveStatus;
import com.example.EmployeeManagement.enums.Role;

public class ConvertToEntity {

    public static User convertToUserEntity(UserRequest user){
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setAddress(user.getAddress());
        newUser.setPhone(user.getPhone());
        newUser.setDept(user.getDept());
        newUser.setManager(null);
        newUser.setEnabled(true);
        newUser.setRole(Role.EMPLOYEE);
        return newUser;
    }

    public static LeaveRequest convertToLeaveRequest(
            LeaveRequestDto leaveRequestDto,
            LeaveType leaveType,
            User user
    ){
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setStartDate(leaveRequestDto.getStartDate());
        leaveRequest.setEndDate(leaveRequestDto.getEndDate());
        leaveRequest.setReason(leaveRequestDto.getReason());
        leaveRequest.setEmployee(user);
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setStatus(LeaveStatus.PENDING);
        return leaveRequest;
    }

    public static LeaveApproval covertToLeaveApproval(
            LeaveApprovalDto dto,
            LeaveRequest leaveRequest,
            User approver,
            ApproverRole role
    ){
        LeaveApproval leaveApproval = new LeaveApproval();
        leaveApproval.setStatus(dto.getStatus());
        leaveApproval.setApprover(approver);
        leaveApproval.setLeaveRequest(leaveRequest);
        leaveApproval.setApproverRole(role);
        leaveApproval.setComments(dto.getComment());

        return leaveApproval;

    }


}
