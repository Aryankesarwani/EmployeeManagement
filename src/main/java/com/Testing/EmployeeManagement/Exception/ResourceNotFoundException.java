package com.Testing.EmployeeManagement.Exception;

public class ResourceNotFoundException extends Throwable {
    public ResourceNotFoundException(String exp){
        super(exp);
    }
}
