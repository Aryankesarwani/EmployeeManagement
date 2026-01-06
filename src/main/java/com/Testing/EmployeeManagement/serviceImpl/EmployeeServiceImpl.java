package com.Testing.EmployeeManagement.serviceImpl;

import com.Testing.EmployeeManagement.DTO.EmployeeDTO;
import com.Testing.EmployeeManagement.Entity.Employee;
import com.Testing.EmployeeManagement.Exception.ResourceNotFoundException;
import com.Testing.EmployeeManagement.repository.EmployeeRepo;
import com.Testing.EmployeeManagement.service.EmployeeService;
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
        Employee employee = modelMapper.map(employeeDTO,Employee.class);
        Employee savedEmployee = employeeRepo.save(employee);
        return savedEmployee;
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) throws ResourceNotFoundException {
        Employee employee = employeeRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not Found"));
        modelMapper.map(employeeDTO,employee);
        Employee updatedEmployee = employeeRepo.save(employee);
        return updatedEmployee;

    }

    @Override
    public Employee getEmployee(Long id) throws ResourceNotFoundException {
        Employee employee = employeeRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not Found"));
        return employee;
    }

    @Override
    public String deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
        return "Employee with id : " + id +" deleted Successfully...";
    }
}
