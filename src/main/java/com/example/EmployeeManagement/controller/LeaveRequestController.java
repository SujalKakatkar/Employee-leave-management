package com.example.EmployeeManagement.controller;


import com.example.EmployeeManagement.dto.LeaveRequestDto;
import com.example.EmployeeManagement.dto.LeaveRequestResponse;
import com.example.EmployeeManagement.service.LeaveRequestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaverequest")
@AllArgsConstructor
public class LeaveRequestController {

    private LeaveRequestService leaveRequestService;

    @PostMapping
    public ResponseEntity<LeaveRequestResponse> createLeaveRequest(@Valid @RequestBody LeaveRequestDto requestDto, Authentication authentication){
        String email = authentication.getName();
        return ResponseEntity.ok(leaveRequestService.requestLeave(requestDto,email));
    }

    @GetMapping("/me")
    public ResponseEntity<List<LeaveRequestResponse>> get(Authentication authentication){

        String email = authentication.getName();
        return ResponseEntity.ok(
                leaveRequestService.getLeaveRequests(email)
        );

    }

}
