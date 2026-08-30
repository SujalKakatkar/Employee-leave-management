package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.service.LeaveBalanceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/leavebalance")
@Validated
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    public LeaveBalanceController(LeaveBalanceService leaveBalanceService) {
        this.leaveBalanceService = leaveBalanceService;
    }

    //add leave balance to new employee
    @PostMapping
    public ResponseEntity<String> addBalance(@Valid @RequestParam Integer userId, @RequestParam @Positive(message = "year must be positive") Integer year){
        leaveBalanceService.createLeaveBalance(userId, year);
        return ResponseEntity.status(HttpStatus.CREATED).body("the balance for employee is added");

    }

}
