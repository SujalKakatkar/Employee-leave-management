package com.example.EmployeeManagement.controller;


//authentication check only

import com.example.EmployeeManagement.dto.user.UserLoginRequest;
import com.example.EmployeeManagement.dto.user.UserLoginResponse;
import com.example.EmployeeManagement.dto.user.UserCreateRequest;
import com.example.EmployeeManagement.dto.user.UserResponse;
import com.example.EmployeeManagement.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest userRequest){
        return ResponseEntity.ok(userService.createEmployee(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

}
