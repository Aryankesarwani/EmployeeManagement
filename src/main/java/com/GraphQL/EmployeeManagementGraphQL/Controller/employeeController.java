package com.GraphQL.EmployeeManagementGraphQL.Controller;

import com.GraphQL.EmployeeManagementGraphQL.DTO.EmployeeDTO;
import com.GraphQL.EmployeeManagementGraphQL.Entity.Employee;
import com.GraphQL.EmployeeManagementGraphQL.Exception.ResourceNotFoundException;
import com.GraphQL.EmployeeManagementGraphQL.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class employeeController {
    @Autowired
    EmployeeService employeeService;

    @MutationMapping("addEmployee")
    public Employee registerEmployee(@Argument EmployeeDTO employeeDTO){
        Employee employee = employeeService.registerEmployee(employeeDTO);
        return employee;
    }

    @MutationMapping("updateEmployee")
    public Employee updateEmployee(@Argument String id, @Argument EmployeeDTO employeeDTO) throws ResourceNotFoundException {
        return employeeService.updateEmployee(id,employeeDTO);
    }

    @QueryMapping("get")
    public Employee getEmployee(@Argument String id) throws ResourceNotFoundException {
        return employeeService.getEmployee(id);
    }
    @QueryMapping("getAll")
    public List<Employee> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @MutationMapping("deleteEmployee")
    public String deleteEmployee(@Argument String id){
        return employeeService.deleteEmployee(id);
    }

}
