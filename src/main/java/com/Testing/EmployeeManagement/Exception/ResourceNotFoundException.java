package com.Testing.EmployeeManagement.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String exp){
        super(exp);
    }
}
