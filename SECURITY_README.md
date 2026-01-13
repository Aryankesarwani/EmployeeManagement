# Employee Management System - Security Implementation

## Overview
This Employee Management System now includes Spring Security with JWT-based authentication and role-based access control (RBAC).

## Security Features

### 1. Authentication
- **JWT Token-based Authentication**: Stateless authentication using JSON Web Tokens
- **Password Encryption**: BCrypt password encoding for secure password storage
- **Token Expiration**: Configurable token expiration (default: 24 hours)

### 2. Authorization
Three roles are available:
- **ROLE_USER**: Basic access to view employee information
- **ROLE_MANAGER**: Can view, create, and update employees
- **ROLE_ADMIN**: Full access including delete operations

### 3. Endpoint Security

#### Public Endpoints (No Authentication Required)
- `POST /auth/register` - Register a new user
- `POST /auth/login` - Login and receive JWT token

#### Protected Endpoints (Authentication Required)
- `POST /employee/register/` - Register employee (ADMIN or MANAGER only)
- `PUT /employee/update/{id}` - Update employee (ADMIN or MANAGER only)
- `GET /employee/get/{id}` - Get employee details (USER, ADMIN, or MANAGER)
- `DELETE /employee/delete/{id}` - Delete employee (ADMIN only)

## Setup Instructions

### 1. Database Setup
The application will automatically create the necessary tables and initialize roles on startup.

### 2. Configuration
Update `application.properties` with your database credentials and JWT secret:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your-very-long-secret-key-at-least-256-bits-long
jwt.expiration=86400000
```

### 3. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

## API Usage Examples

### 1. Register a New User

**Request:**
```http
POST http://localhost:8080/auth/register
Content-Type: application/json

{
    "username": "admin",
    "email": "admin@example.com",
    "password": "admin123",
    "roles": ["admin"]
}
```

**Response:**
```json
{
    "message": "User registered successfully!"
}
```

**Note:** Available roles: `"user"`, `"admin"`, `"manager"`. If no roles are specified, `ROLE_USER` is assigned by default.

### 2. Login

**Request:**
```http
POST http://localhost:8080/auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "admin123"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer"
}
```

### 3. Access Protected Endpoints

Use the token received from login in the Authorization header:

**Request:**
```http
GET http://localhost:8080/employee/get/123
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 4. Register an Employee (ADMIN/MANAGER only)

**Request:**
```http
POST http://localhost:8080/employee/register/
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "address": "123 Main St",
    "designation": "Software Engineer",
    "CTC": 75000.0
}
```

## Testing with cURL

### Register User
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "test123",
    "roles": ["user"]
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "test123"
  }'
```

### Access Protected Endpoint
```bash
curl -X GET http://localhost:8080/employee/get/123 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

## Testing with Postman

1. **Register a user**: Send POST request to `/auth/register`
2. **Login**: Send POST request to `/auth/login` and copy the token
3. **Set Authorization**: In Postman, go to Authorization tab, select "Bearer Token", and paste your token
4. **Access protected endpoints**: All employee endpoints will now work with the token

## Security Architecture

### Components

1. **SecurityConfig**: Main Spring Security configuration
   - Configures HTTP security
   - Defines public and protected endpoints
   - Sets up JWT filter

2. **JwtUtil**: JWT token management
   - Token generation
   - Token validation
   - Extract user information from token

3. **JwtAuthenticationFilter**: Request filter
   - Intercepts all requests
   - Validates JWT tokens
   - Sets authentication context

4. **UserDetailsServiceImpl**: User authentication
   - Loads user details from database
   - Used by Spring Security for authentication

5. **DataInitializer**: Database initialization
   - Creates default roles on application startup

## Troubleshooting

### Common Issues

1. **"Access Denied" errors**
   - Ensure you're using a valid JWT token
   - Check that your user has the required role

2. **"Invalid JWT token" errors**
   - Token might be expired (default: 24 hours)
   - Login again to get a new token

3. **"Role not found" errors**
   - Ensure the application has run at least once to initialize roles
   - Check database for roles table

## Security Best Practices

1. **Never commit your JWT secret**: Change the secret key in production
2. **Use HTTPS in production**: JWT tokens should be transmitted over secure connections
3. **Implement token refresh**: Consider adding refresh token functionality for better UX
4. **Add rate limiting**: Protect login endpoints from brute force attacks
5. **Audit logging**: Log all security-related events

## Dependencies

- Spring Boot 4.0.1
- Spring Security
- JJWT 0.12.3
- BCrypt password encoder
- MySQL Connector

## License
This project is for learning purposes.

