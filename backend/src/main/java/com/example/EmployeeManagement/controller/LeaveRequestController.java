package com.example.EmployeeManagement.controller;


import com.example.EmployeeManagement.dto.LeaveReviewResponse;
import com.example.EmployeeManagement.dto.LeaveRequestCreateRequest;
import com.example.EmployeeManagement.dto.LeaveRequestResponse;
import com.example.EmployeeManagement.service.LeaveApprovalService;
import com.example.EmployeeManagement.service.LeaveRequestService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaverequest")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;
    private final LeaveApprovalService leaveApprovalService;

    public LeaveRequestController(LeaveRequestService leaveRequestService, LeaveApprovalService leaveApprovalService) {
        this.leaveRequestService = leaveRequestService;
        this.leaveApprovalService = leaveApprovalService;
    }


    @PostMapping
    public ResponseEntity<LeaveRequestResponse> createLeaveRequest(@Valid @RequestBody LeaveRequestCreateRequest requestDto, Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(leaveRequestService.requestLeave(requestDto,email));
    }



    @GetMapping("/me")
    public ResponseEntity<List<LeaveRequestResponse>> get(Authentication authentication){

        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.OK).body(
                leaveRequestService.getLeaveRequests(email)
        );
    }

    @PatchMapping("/review")
    public ResponseEntity<String> reviewLeaveRequest(@Valid @RequestBody LeaveReviewResponse leaveReviewResponse, Authentication authentication){
        String email = authentication.getName();
          leaveApprovalService.reviewTheRequest(leaveReviewResponse, email);

        return ResponseEntity.status(HttpStatus.OK).body(
                "done with request"
        );

    }

}
