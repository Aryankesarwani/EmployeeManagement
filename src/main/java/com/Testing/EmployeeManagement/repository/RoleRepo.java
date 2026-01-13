package com.Testing.EmployeeManagement.repository;

import com.Testing.EmployeeManagement.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Role.RoleName name);
}

