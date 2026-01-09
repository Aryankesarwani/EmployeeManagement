package com.Testing.EmployeeManagement.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    String name;
    String email;
    String phone;
    String address;
    String designation;
    Float CTC;
}
