package com.GraphQL.EmployeeManagementGraphQL.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Employee", indexes = {
    @Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_phone", columnList = "phone"),
    @Index(name = "idx_designation", columnList = "designation")
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    
    @Column(nullable = false)
    String name;
//    @Column(unique = false)
    String email;
//    @Column(unique = false)
    String phone;
    String address;
    String designation;
    Float CTC;
}