# User Management System

This project implements a User Management and Role Assignment System following Clean Architecture principles with Spring Boot.

## Project Structure

The application follows Robert C. Martin's Clean Architecture with clear separation of:

- **Domain Layer (Entities)**: Core business objects with no external dependencies
- **Application Layer (Use Cases)**: Contains business logic and defines ports (interfaces)
- **Infrastructure Layer (Adapters)**: Implements ports and interacts with frameworks
- **Configuration Layer**: Sets up dependency injection and other system configurations

## Features

- Create Users with name and email
- Create Roles with role name
- Assign Roles to Users
- Remove Roles from Users
- Retrieve User details with their assigned Roles
- Proper validation and error handling

## Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (in-memory)
- JUnit 5 and Mockito for testing

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.x or higher

### Running the Application

1. Clone the repository
2. Navigate to the project root directory
3. Run the following command:

```bash
mvn spring-boot:run
```

The application will start on port 8080.

### Accessing H2 Console

The H2 console is enabled and can be accessed at:

```
http://localhost:8080/h2-console
```

Use the following credentials:
- JDBC URL: `jdbc:h2:mem:userdb`
- Username: `sa`
- Password: `password`

## API Endpoints

### User Management

- **Create User**
    - `POST /users`
    - Request Body: `{ "name": "John Doe", "email": "john@example.com" }`
    - Response: Created User ID

- **Get User Details**
    - `GET /users/{id}`
    - Response: User details including assigned roles

- **Get All Users**
    - `GET /users`
    - Response: List of all users

### Role Management

- **Create Role**
    - `POST /roles`
    - Request Body: `{ "roleName": "ADMIN" }`
    - Response: Created Role ID

- **Get Role Details**
    - `GET /roles/{id}`
    - Response: Role details

- **Get All Roles**
    - `GET /roles`
    - Response: List of all roles

### User-Role Management

- **Assign Role to User**
    - `POST /users/{userId}/assign-role/{roleId}`
    - Response: Success message

- **Remove Role from User**
    - `POST /users/{userId}/remove-role/{roleId}`
    - Response: Success message

## Running Tests

To run the tests, execute:

```bash
mvn test
```

## Clean Architecture Implementation Details

### Domain Layer
Contains the core business entities:
- `User.java`: Represents a user with name, email, and roles
- `Role.java`: Represents a role with role name

### Application Layer
Contains the business logic and defines repository interfaces:
- `UserService.java`: Handles user-related operations
- `RoleService.java`: Handles role-related operations
- `UserRepository.java`: Interface for user data access
- `RoleRepository.java`: Interface for role data access

### Infrastructure Layer
Contains implementations of the repository interfaces and controllers:
- Database Adapters: Implements repository interfaces using Spring Data JPA
- REST Controllers: Exposes the functionality as REST endpoints
- DTO Classes: Used for input/output data transformation
- Exception Handling: Global exception handler for consistent error responses

### Configuration Layer
Handles bean configurations and dependency injection:
- `BeanConfig.java`: Configures service beans