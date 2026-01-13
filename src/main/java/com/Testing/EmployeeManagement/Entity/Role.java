package com.Testing.EmployeeManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    RoleName name;
    
    public enum RoleName {
        ROLE_USER,
        ROLE_ADMIN,
        ROLE_MANAGER
    }
}

