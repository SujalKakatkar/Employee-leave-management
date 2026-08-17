package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.LeaveTypeCreateRequest;
import com.example.EmployeeManagement.dto.LeaveTypeResponse;
import com.example.EmployeeManagement.entity.LeaveType;
import com.example.EmployeeManagement.enums.LeaveStatus;
import com.example.EmployeeManagement.service.LeaveTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leavetype")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    public LeaveTypeController(LeaveTypeService leaveTypeService) {
        this.leaveTypeService = leaveTypeService;
    }


    @PostMapping
    public ResponseEntity<LeaveTypeResponse> createLeave(@RequestBody LeaveTypeCreateRequest leaveType) {
        return ResponseEntity.ok(leaveTypeService.createLeave(leaveType));
    }

    @GetMapping
    public ResponseEntity<List<LeaveTypeResponse>> getAllTypes(){
        return ResponseEntity.ok(
                leaveTypeService.getAllTypes()
        );
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteLeaveType(@RequestParam String name) {
        leaveTypeService.deleteLeave(name);
        return ResponseEntity.ok("the leave type is deleted");
    }
}
