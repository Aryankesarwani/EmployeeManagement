package com.Testing.EmployeeManagement.Controller;

import com.Testing.EmployeeManagement.DTO.EmployeeDTO;
import com.Testing.EmployeeManagement.Entity.Employee;
import com.Testing.EmployeeManagement.Exception.ResourceNotFoundException;
import com.Testing.EmployeeManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee/")
public class employeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("register/")
    public ResponseEntity<Employee> registerEmployee(@RequestBody EmployeeDTO employeeDTO){

        return new ResponseEntity<>(employeeService.registerEmployee(employeeDTO), HttpStatus.CREATED);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) throws ResourceNotFoundException {
        return new ResponseEntity<>(employeeService.updateEmployee(id,employeeDTO),HttpStatus.ACCEPTED);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(employeeService.getEmployee(id),HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        return new ResponseEntity<>(employeeService.deleteEmployee(id),HttpStatus.ACCEPTED);
    }

}
