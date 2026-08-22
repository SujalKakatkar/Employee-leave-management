package com.example.EmployeeManagement.controller;


import com.example.EmployeeManagement.dto.HolidayCreateRequest;
import com.example.EmployeeManagement.dto.HolidayResponse;
import com.example.EmployeeManagement.entity.Holiday;
import com.example.EmployeeManagement.entity.LeaveType;
import com.example.EmployeeManagement.service.HolidayService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

    private final HolidayService holidayService;


    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PostMapping
    public ResponseEntity<HolidayResponse> createHoliday(@Valid @RequestBody HolidayCreateRequest holiday) {
        return ResponseEntity.status(HttpStatus.CREATED).body(holidayService.createHoliday(holiday));
    }

    @GetMapping
    public ResponseEntity<List<HolidayResponse>> getAllHolidays(){
        return ResponseEntity.status(HttpStatus.OK).body(holidayService.getAllHolidays());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLeaveType(@RequestParam String name) {
        holidayService.deleteHoliday(name);
        return ResponseEntity.status(HttpStatus.OK).body("the leave type is deleted");
    }
}
