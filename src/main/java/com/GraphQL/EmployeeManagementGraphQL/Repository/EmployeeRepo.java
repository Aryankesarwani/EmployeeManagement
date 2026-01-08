package com.GraphQL.EmployeeManagementGraphQL.Repository;

import com.GraphQL.EmployeeManagementGraphQL.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,String> {

}
