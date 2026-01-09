package com.Testing.EmployeeManagement.service;

import com.Testing.EmployeeManagement.DTO.EmployeeDTO;
import com.Testing.EmployeeManagement.Entity.Employee;
import com.Testing.EmployeeManagement.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    Employee registerEmployee(EmployeeDTO employeeDTO);

    Employee updateEmployee(String id, EmployeeDTO employeeDTO) throws ResourceNotFoundException;

    Employee getEmployee(String id) throws ResourceNotFoundException;

    String deleteEmployee(String id) throws ResourceNotFoundException;
}
