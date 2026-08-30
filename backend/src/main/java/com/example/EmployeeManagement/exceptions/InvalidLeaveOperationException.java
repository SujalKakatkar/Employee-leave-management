package com.example.EmployeeManagement.exceptions;

public class InvalidLeaveOperationException extends RuntimeException{
    public InvalidLeaveOperationException(String message) {
        super(message);
    }
}
