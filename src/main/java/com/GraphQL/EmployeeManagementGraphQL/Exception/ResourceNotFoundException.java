package com.GraphQL.EmployeeManagementGraphQL.Exception;

public class ResourceNotFoundException extends Throwable {
    public ResourceNotFoundException(String exp){
        super(exp);
    }
}
