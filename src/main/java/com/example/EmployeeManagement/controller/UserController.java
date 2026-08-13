package com.example.EmployeeManagement.controller;


//authentication check only

import com.example.EmployeeManagement.dto.LoginRequest;
import com.example.EmployeeManagement.dto.UserRequest;
import com.example.EmployeeManagement.dto.UserResponse;
import com.example.EmployeeManagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.createEmployee(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

}
