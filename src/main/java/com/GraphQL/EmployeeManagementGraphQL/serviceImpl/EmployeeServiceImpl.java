package com.GraphQL.EmployeeManagementGraphQL.serviceImpl;

import com.GraphQL.EmployeeManagementGraphQL.DTO.EmployeeDTO;
import com.GraphQL.EmployeeManagementGraphQL.Entity.Employee;
import com.GraphQL.EmployeeManagementGraphQL.Exception.ResourceNotFoundException;
import com.GraphQL.EmployeeManagementGraphQL.Repository.EmployeeRepo;
import com.GraphQL.EmployeeManagementGraphQL.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Employee registerEmployee(EmployeeDTO employeeDTO) {
        System.out.println("I am in ServiceImpl");
        Employee employee = modelMapper.map(employeeDTO,Employee.class);
        Employee savedEmployee = employeeRepo.save(employee);
        System.out.println(employeeDTO.getName());
        return savedEmployee;
    }

    @Override
    public Employee updateEmployee(String id, EmployeeDTO employeeDTO) throws ResourceNotFoundException {
        Employee employee = employeeRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not Found"));
        modelMapper.map(employeeDTO,employee);
        Employee updatedEmployee = employeeRepo.save(employee);
        return updatedEmployee;

    }

    @Override
    public Employee getEmployee(String id) throws ResourceNotFoundException {
        Employee employee = employeeRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not Found"));
        return employee;
    }

    @Override
    public String deleteEmployee(String id) {
        employeeRepo.deleteById(id);
        return "Employee with id : " + id +" deleted Successfully...";
    }
}
