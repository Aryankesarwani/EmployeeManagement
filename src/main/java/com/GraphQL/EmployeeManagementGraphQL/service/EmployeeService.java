package com.GraphQL.EmployeeManagementGraphQL.service;

import com.GraphQL.EmployeeManagementGraphQL.DTO.EmployeeDTO;
import com.GraphQL.EmployeeManagementGraphQL.Entity.Employee;
import com.GraphQL.EmployeeManagementGraphQL.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    Employee registerEmployee(EmployeeDTO employeeDTO);

    Employee updateEmployee(String id, EmployeeDTO employeeDTO) throws ResourceNotFoundException;

    Employee getEmployee(String id) throws ResourceNotFoundException;

    String deleteEmployee(String id);
}
