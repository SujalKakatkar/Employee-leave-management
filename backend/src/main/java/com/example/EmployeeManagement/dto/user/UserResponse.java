package com.example.EmployeeManagement.dto.user;

import com.example.EmployeeManagement.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Integer userId;
    private String name;

    private String username;

    private String email;
    private Role role;
    private String phone;
    private String address;
    private String dept;

}
