package com.Testing.EmployeeManagement.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    String name;
    String email;
    String phone;
    String address;
    String designation;
    Double CTC;
}
