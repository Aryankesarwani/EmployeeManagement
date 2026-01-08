package com.GraphQL.EmployeeManagementGraphQL.DTO;

import lombok.*;
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
    Float CTC;
}
