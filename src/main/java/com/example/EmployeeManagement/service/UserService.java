package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.LoginRequest;
import com.example.EmployeeManagement.dto.UserRequest;
import com.example.EmployeeManagement.dto.UserResponse;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.mapper.ConvertToDto;
import com.example.EmployeeManagement.mapper.ConvertToEntity;

import com.example.EmployeeManagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createEmployee(UserRequest user) {
        //if user exists with same username
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("username already used");
        }
        //if user exists with same email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("user not found");
        }

        //finally mapping the userdata to entity
        User newUser = ConvertToEntity.convertToUserEntity(user);
        //hashing the password
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));


        User temp = userRepository.save(newUser);

        return ConvertToDto.convertToUserResponse(temp, null);
    }

    public UserResponse loginUser(LoginRequest login) {
        User temp = userRepository.findByUsername(login.getUsername()).orElseThrow(
                () -> new RuntimeException("user not found")
        );


        boolean isMatch = passwordEncoder.matches(login.getPassword(), temp.getPassword());
        if (!isMatch) throw new RuntimeException("Wrong password");
        String token = jwtService.generateToken(temp);

        return ConvertToDto.convertToUserResponse(temp, token);
    }
}
