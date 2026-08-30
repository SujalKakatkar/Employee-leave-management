package com.example.EmployeeManagement.exceptions;

public class RoleMismatchException extends RuntimeException{
    public RoleMismatchException(String message) {
        super(message);
    }
}
