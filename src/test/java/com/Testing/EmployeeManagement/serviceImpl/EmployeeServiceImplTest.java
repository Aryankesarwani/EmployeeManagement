package com.Testing.EmployeeManagement.serviceImpl;

import com.Testing.EmployeeManagement.DTO.EmployeeDTO;
import com.Testing.EmployeeManagement.Entity.Employee;
import com.Testing.EmployeeManagement.Exception.ResourceNotFoundException;
import com.Testing.EmployeeManagement.repository.EmployeeRepo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeServiceImplTest {
    @Mock
    EmployeeRepo employeeRepo;

    @Mock
    ModelMapper modelMapper;

    Employee employee;

    EmployeeDTO employeeDTO;

    @InjectMocks
    EmployeeServiceImpl employeeServiceImpl;

    public static Stream<Arguments> updateEmployee() {
        return Stream.of(Arguments.of(1L,new EmployeeDTO("Mohit","mohit_sahu@gmail.com","3245675432","Bangaluru","Back-End Developer",12.9)));
    }

    @BeforeEach
    void init(){
        employee = new Employee();
        employee.setName("Aryan Kesarwani");
        employee.setEmail("aryan_kesarwani@gmail.com");
        employee.setAddress("Bangalore");
        employee.setPhone("999999999");
        employee.setCTC(7.8);
        employee.setDesignation("Back-End Developer");

        employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Aryan Kesarwani");
        employeeDTO.setEmail("aryan_kesarwani@gmail.com");
        employeeDTO.setAddress("Bangalore");
        employeeDTO.setPhone("999999999");
        employeeDTO.setCTC(7.8);
        employeeDTO.setDesignation("Back-End Developer");
    }

    @Test
    void registerEmployeeTest(){

        EmployeeDTO savedEmployeeDTO = new EmployeeDTO();
        savedEmployeeDTO.setName("Aryan Kesarwani");
        savedEmployeeDTO.setEmail("aryan_kesarwani@gmail.com");
        savedEmployeeDTO.setAddress("Bangalore");
        savedEmployeeDTO.setPhone("999999999");
        savedEmployeeDTO.setCTC(7.8);
        savedEmployeeDTO.setDesignation("Back-End Developer");

        Employee savedEmployee = new Employee();
        savedEmployee.setId(1L);
        savedEmployee.setName("Aryan Kesarwani");
        savedEmployee.setEmail("aryan_kesarwani@gmail.com");
        savedEmployee.setAddress("Bangalore");
        savedEmployee.setPhone("999999999");
        savedEmployee.setCTC(7.8);
        savedEmployee.setDesignation("Back-End Developer");


        Mockito.when(modelMapper.map(employeeDTO,Employee.class)).thenReturn(employee);

        Mockito.when(employeeRepo.save(employee)).thenReturn(savedEmployee);

//        Mockito.when(modelMapper.map(savedEmployee,EmployeeDTO.class)).thenReturn(savedEmployeeDTO);

        Employee result = employeeServiceImpl.registerEmployee(employeeDTO);
        assertNotNull(result);
        assertEquals("Aryan Kesarwani",result.getName());
        assertSame(savedEmployee,result);
        Mockito.verify(employeeRepo,Mockito.times(1)).save(employee);
    }

    @ParameterizedTest
    @MethodSource("updateEmployee")
    void updateEmployeeTest(Long id,EmployeeDTO employeeDTO) throws ResourceNotFoundException {

        this.employeeDTO = employeeDTO;
        Mockito.when(employeeRepo.findById(id)).thenReturn(Optional.of(employee));

        Mockito.doAnswer(invocation->{  
            Employee target = invocation.getArgument(1);
            target.setName(employeeDTO.getName());
            target.setEmail(employeeDTO.getEmail());
            target.setAddress(employeeDTO.getAddress());
            target.setPhone(employeeDTO.getPhone());
            target.setCTC(employeeDTO.getCTC());
            target.setDesignation(employeeDTO.getDesignation());
            return null;
        }).when(modelMapper).map(employeeDTO,employee);

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(id);
        updatedEmployee.setName(employeeDTO.getName());
        updatedEmployee.setEmail(employeeDTO.getEmail());
        updatedEmployee.setAddress(employeeDTO.getAddress());
        updatedEmployee.setPhone(employeeDTO.getPhone());
        updatedEmployee.setCTC(employeeDTO.getCTC());
        updatedEmployee.setDesignation(employeeDTO.getDesignation());

        Mockito.when(employeeRepo.save(Mockito.any(Employee.class))).thenReturn(updatedEmployee);
//        Mockito.when(modelMapper.map(updatedEmployee,EmployeeDTO.class)).thenReturn(employeeDTO);

        
        Employee result = employeeServiceImpl.updateEmployee(id,employeeDTO);


        assertNotNull(result);
        assertEquals(updatedEmployee.getName(),result.getName());
        assertEquals(updatedEmployee.getEmail(),result.getEmail());
        
        Mockito.verify(employeeRepo).save(Mockito.any(Employee.class));
        Mockito.verify(employeeRepo).findById(id);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L})
    void getEmployeeTest(long id) throws ResourceNotFoundException{
        Mockito.when(employeeRepo.findById(id)).thenReturn(Optional.of(employee));
        Employee result = employeeServiceImpl.getEmployee(id);

        assertNotNull(result);
        assertEquals(employee.getEmail(),result.getEmail());
        assertSame(employee,result);
        Mockito.verify(employeeRepo).findById(id);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L})
    void deleteEmployee(long id){
        employeeServiceImpl.deleteEmployee(id);
        Mockito.verify(employeeRepo).deleteById(id);
    }

}
