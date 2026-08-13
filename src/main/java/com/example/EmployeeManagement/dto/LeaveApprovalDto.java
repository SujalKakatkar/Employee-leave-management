package com.example.EmployeeManagement.dto;

import com.example.EmployeeManagement.enums.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaveApprovalDto {

    private Integer leaveRequestId;
    private LeaveStatus status;
    private String comment;


}
