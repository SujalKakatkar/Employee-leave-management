package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.entity.LeaveType;
import com.example.EmployeeManagement.enums.LeaveStatus;
import com.example.EmployeeManagement.service.LeaveTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leavetype")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    public LeaveTypeController(LeaveTypeService leaveTypeService) {
        this.leaveTypeService = leaveTypeService;
    }


    @PostMapping
    public ResponseEntity<LeaveType> createLeave(@RequestBody LeaveType leaveType) {
        return ResponseEntity.ok(leaveTypeService.createLeave(leaveType));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteLeaveType(@RequestParam String name) {
        leaveTypeService.deleteLeave(name);
        return ResponseEntity.ok("the leave type is deleted");
    }
}
