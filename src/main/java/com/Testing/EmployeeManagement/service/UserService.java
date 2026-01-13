package com.Testing.EmployeeManagement.service;

import com.Testing.EmployeeManagement.DTO.LoginRequest;
import com.Testing.EmployeeManagement.DTO.RegisterRequest;

public interface UserService {
    String registerUser(RegisterRequest registerRequest);
    String loginUser(LoginRequest loginRequest);
}

