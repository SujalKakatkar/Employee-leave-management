package com.example.EmployeeManagement.controller;



import com.example.EmployeeManagement.dto.user.UserResponse;
import com.example.EmployeeManagement.service.HRService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/hr")
public class HRController {

    private final HRService hrService;


    public HRController(HRService hrService) {
        this.hrService = hrService;
    }

    @PutMapping("/promote")
    public ResponseEntity<String> promoteToManger(@RequestParam Integer empId, Authentication authentication){
        String emailOfHR = authentication.getName();
        hrService.promoteManger(empId, emailOfHR);
        return ResponseEntity.ok("the employee is prompted to manger");
    }

    @PatchMapping("/assign")
    public ResponseEntity<String> promoteToManger(@RequestParam Integer empId,@RequestParam Integer managerId){

        hrService.assignManger(empId, managerId);
        return ResponseEntity.ok("the employee is prompted to manger");
    }

    //all employees including managers
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAll(Authentication authentication){

        return ResponseEntity.ok(
                hrService.getAllEmployees()
        );

    }

    //all managers
    @GetMapping("/managers")
    public ResponseEntity<List<UserResponse>> getAllManagers(Authentication authentication){

        return ResponseEntity.ok(
                hrService.getAllManagers()
        );

    }



    //disable user profile
    @PatchMapping("/disable")
    public ResponseEntity<String> disableEmployee(@RequestParam String username){
            hrService.disableEmployee(username);
            return ResponseEntity.ok(
                    "employee is disabled"
            );
    }

    //demote to employee



}
