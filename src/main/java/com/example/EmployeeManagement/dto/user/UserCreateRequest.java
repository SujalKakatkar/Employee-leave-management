package com.example.EmployeeManagement.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateRequest {
    @NotBlank(message = "name is required")
    @Size(min = 3, message = "name must be at least 3 characters")
    private String name;

    @NotBlank(message = "username is required")
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String dept;

}
