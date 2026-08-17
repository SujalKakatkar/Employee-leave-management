package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.LeaveBalanceResponse;
import com.example.EmployeeManagement.service.LeaveBalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leavebalance")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    public LeaveBalanceController(LeaveBalanceService leaveBalanceService) {
        this.leaveBalanceService = leaveBalanceService;
    }

    //add leave balance to new employee
    @PostMapping
    public ResponseEntity<String> addBalance(@RequestParam String username, @RequestParam Integer year){
        leaveBalanceService.createLeaveBalance(username, year);
        return ResponseEntity.ok("done");

    }

    @GetMapping
    public ResponseEntity<List<LeaveBalanceResponse>> getAllBalance(){
        return ResponseEntity.ok(leaveBalanceService.getAll());
    }


    @GetMapping("/{username}")
    public ResponseEntity<List<LeaveBalanceResponse>> getAllTypesByUsername(@PathVariable String username){
        return  ResponseEntity.ok(leaveBalanceService.getAllBalanceByUsername(username));
    }

}
