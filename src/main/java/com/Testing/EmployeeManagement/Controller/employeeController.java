package com.Testing.EmployeeManagement.Controller;

import com.Testing.EmployeeManagement.DTO.EmployeeDTO;
import com.Testing.EmployeeManagement.Entity.Employee;
import com.Testing.EmployeeManagement.Exception.ResourceNotFoundException;
import com.Testing.EmployeeManagement.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee/")
@Slf4j
public class employeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("register/")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Object> registerEmployee(@RequestBody EmployeeDTO employeeDTO){

        return new ResponseEntity<>(employeeService.registerEmployee(employeeDTO), HttpStatus.CREATED);
    }
    
    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Object> updateEmployee(@PathVariable String id, @RequestBody EmployeeDTO employeeDTO) throws ResourceNotFoundException {
        try {
            return new ResponseEntity<>(employeeService.updateEmployee(id, employeeDTO), HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public ResponseEntity<Object> getEmployee(@PathVariable String id) {
        try{

            return new ResponseEntity<>(employeeService.getEmployee(id),HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteEmployee(@PathVariable String id) throws ResourceNotFoundException{
        try{
            return new ResponseEntity<>(employeeService.deleteEmployee(id),HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
