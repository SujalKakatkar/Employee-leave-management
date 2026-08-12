package com.example.EmployeeManagement.mapper;


import com.example.EmployeeManagement.dto.LeaveBalanceResponse;
import com.example.EmployeeManagement.dto.LeaveRequestDto;
import com.example.EmployeeManagement.dto.LeaveRequestResponse;
import com.example.EmployeeManagement.dto.UserResponse;
import com.example.EmployeeManagement.entity.LeaveBalance;
import com.example.EmployeeManagement.entity.LeaveRequest;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.enums.Role;

public class ConvertToDto {

    public static UserResponse convertToUserResponse(User user,String token){
        UserResponse newUser = new UserResponse();
        newUser.setUserId(user.getUserId());
        newUser.setName(user.getName());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setAddress(user.getAddress());
        newUser.setPhone(user.getPhone());
        newUser.setDept(user.getDept());
        newUser.setRole(Role.EMPLOYEE);
        newUser.setToken(token);
        return newUser;
    }

    public static LeaveBalanceResponse convertToLeaveBalanceResponse(LeaveBalance leaveBalance){
        LeaveBalanceResponse newResponse = new LeaveBalanceResponse();
        newResponse.setBalanceId(leaveBalance.getBalanceId());
        newResponse.setYear(leaveBalance.getYear());
        newResponse.setUsername(leaveBalance.getUser().getUsername());
        newResponse.setLeaveTypeName(leaveBalance.getLeaveType().getName());
        newResponse.setAllocatedDays(leaveBalance.getAllocatedDays());
        newResponse.setUsedDays(leaveBalance.getUsedDays());

        return newResponse;
    }

    public static LeaveRequestResponse convertToLeaveRequestDto(LeaveRequest leaveRequest){
        LeaveRequestResponse leaveRequestResponse = new LeaveRequestResponse();
        leaveRequestResponse.setRequestId(leaveRequest.getRequestId());
        leaveRequestResponse.setStartDate(leaveRequest.getStartDate());
        leaveRequestResponse.setEndDate(leaveRequest.getEndDate());
        leaveRequestResponse.setNumberOfDays(leaveRequest.getNumberOfDays());
        leaveRequestResponse.setReason(leaveRequest.getReason());
        leaveRequestResponse.setStatus(leaveRequest.getStatus());
        return leaveRequestResponse;
    }
}
