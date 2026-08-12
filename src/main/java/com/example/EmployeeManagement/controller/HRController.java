package com.example.EmployeeManagement.controller;



import com.example.EmployeeManagement.service.HRService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

//responsibility
//add new employee or manager
//update  employee or manager
//delete employee and manager
//getData or employee or manger /allEmployees /allmanagers and both
//disable enable
//approve and reject leave

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

}
