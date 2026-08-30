package com.example.EmployeeManagement.exceptions;

public class StatusMismatchException extends RuntimeException{
    public StatusMismatchException(String message) {
        super(message);
    }
}
