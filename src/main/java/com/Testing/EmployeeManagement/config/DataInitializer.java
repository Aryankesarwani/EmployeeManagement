package com.Testing.EmployeeManagement.config;

import com.Testing.EmployeeManagement.Entity.Role;
import com.Testing.EmployeeManagement.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Autowired
    private RoleRepo roleRepo;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            // Initialize roles if they don't exist
            if (roleRepo.findByName(Role.RoleName.ROLE_USER).isEmpty()) {
                Role userRole = new Role();
                userRole.setName(Role.RoleName.ROLE_USER);
                roleRepo.save(userRole);
            }

            if (roleRepo.findByName(Role.RoleName.ROLE_ADMIN).isEmpty()) {
                Role adminRole = new Role();
                adminRole.setName(Role.RoleName.ROLE_ADMIN);
                roleRepo.save(adminRole);
            }

            if (roleRepo.findByName(Role.RoleName.ROLE_MANAGER).isEmpty()) {
                Role managerRole = new Role();
                managerRole.setName(Role.RoleName.ROLE_MANAGER);
                roleRepo.save(managerRole);
            }
        };
    }
}

