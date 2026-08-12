package com.example.EmployeeManagement.controller;


import com.example.EmployeeManagement.entity.Holiday;
import com.example.EmployeeManagement.entity.LeaveType;
import com.example.EmployeeManagement.service.HolidayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

    private HolidayService holidayService;


    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PostMapping
    public ResponseEntity<Holiday> createHoliday(@RequestBody Holiday holiday) {
        return ResponseEntity.ok(holidayService.createHoliday(holiday));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLeaveType(@RequestParam String name) {
        holidayService.deleteHoliday(name);
        return ResponseEntity.ok("the leave type is deleted");
    }
}
