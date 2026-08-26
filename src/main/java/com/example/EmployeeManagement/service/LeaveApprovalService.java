package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.LeaveReviewResponse;
import com.example.EmployeeManagement.entity.LeaveReview;
import com.example.EmployeeManagement.entity.LeaveRequest;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.enums.ApproverRole;
import com.example.EmployeeManagement.enums.LeaveStatus;
import com.example.EmployeeManagement.enums.Role;
import com.example.EmployeeManagement.exceptions.ResourceNotFoundException;
import com.example.EmployeeManagement.exceptions.RoleMismatchException;
import com.example.EmployeeManagement.exceptions.StatusMismatchException;
import com.example.EmployeeManagement.mapper.MapToEntity;
import com.example.EmployeeManagement.repository.LeaveReviewRepository;
import com.example.EmployeeManagement.repository.LeaveRequestRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.example.EmployeeManagement.enums.ApproverRole.MANAGER;

@Service
public class LeaveApprovalService {

    private final LeaveReviewRepository leaveReviewRepository;
    private final UserRepository userRepository;
    private final LeaveBalanceService leaveBalanceService;
    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveApprovalService(LeaveReviewRepository leaveReviewRepository, UserRepository userRepository, LeaveBalanceService leaveBalanceService, LeaveRequestRepository leaveRequestRepository) {
        this.leaveReviewRepository = leaveReviewRepository;
        this.userRepository = userRepository;
        this.leaveBalanceService = leaveBalanceService;
        this.leaveRequestRepository = leaveRequestRepository;
    }


    private ApproverRole toApproverRole(Role role) {
        return switch (role) {
            case HR -> ApproverRole.HR;
            case MANAGER -> MANAGER;
            default -> throw new RoleMismatchException("This role cannot approve leave requests: " + role);
        };
    }


    //review
    @Transactional
    public void reviewTheRequest(LeaveReviewResponse leaveReviewResponse, String email){
        User approver = userRepository.findByEmailAndEnabledTrue(email).orElseThrow(
                ()-> new ResourceNotFoundException("user not found")
        );
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveReviewResponse.getLeaveRequestId()).orElseThrow(
                ()-> new ResourceNotFoundException("Request not found")
        );

        //find which user wants the leave and check if he is th same as approve to deny
        User leaveAppliedUser = leaveRequest.getEmployee();

        if(Objects.equals(approver.getUserId(), leaveAppliedUser.getUserId())){
            throw  new RoleMismatchException("you can't approve or reject your request");
        }

        if(leaveRequest.getStatus() != LeaveStatus.PENDING){
            throw new StatusMismatchException("the request may be reviewed by someone already");
        }

        if(leaveReviewResponse.getStatus() == LeaveStatus.APPROVED){
            leaveBalanceService.updateLeaveBalance(
                    leaveAppliedUser.getUserId(),
                    leaveRequest.getLeaveType().getLeaveTypeId(),
                    leaveRequest.getNumberOfDays(),
                    leaveRequest.getStartDate().getYear()

            );
        }


        LeaveReview leaveReview = MapToEntity.mapToLeaveReview(leaveReviewResponse,leaveRequest,approver,toApproverRole(approver.getRole()));
        leaveReviewRepository.save(leaveReview);

        leaveRequest.setStatus(leaveReviewResponse.getStatus());
        leaveRequestRepository.save(leaveRequest);



    }

}
