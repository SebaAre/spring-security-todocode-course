# Spring Security TodoCode Course

Exercise from the TodoCode Academy Spring Security course by Luisina de Paula.

**Academy Website:** https://todocodeacademy.com/

---

## Exercise 01 - Educational Platform with JWT Authentication

RESTful API for managing users, roles, and permissions in an educational platform with JWT-based authentication.

---

## What it does

- User registration and authentication
- JWT token generation and validation
- Role-based access control (RBAC)
- Manage users with roles: STUDENT, PROFESSOR, ADMINISTRATOR
- Secure endpoints with Spring Security

---

## Database

**User:** id, username, password, enabled, accountNotExpired, accountNotLocked, credentialNotExpired, roles

**Role:** id, name, permissions

**Permission:** id, name

**Relationships:**
- User ↔ Role (ManyToMany)
- Role ↔ Permission (ManyToMany)

---

## Endpoints

### Authentication (Public)
- `POST /auth/register` - Register new user
- `POST /auth/login` - Login and get JWT token

### Protected Endpoints
- Add authorization header: `Authorization: Bearer <your-jwt-token>`

---

## Setup

1. Create MySQL database: `educative_platform`

2. Edit `application.properties` with your environment variables:
```properties
DB_USERNAME=root
DB_PASSWORD=yourpassword
JWT_SECRET_KEY=your-secret-key-here
```

3. Run SQL script to create initial roles and permissions:
```sql
INSERT INTO permissions (name) VALUES ('READ'), ('CREATE'), ('UPDATE'), ('DELETE');
INSERT INTO roles (name) VALUES ('STUDENT'), ('PROFESSOR'), ('ADMINISTRATOR');
-- Assign permissions to roles
INSERT INTO role_permissions (role_id, permission_id) VALUES 
(3, 1), (3, 2), (3, 3), (3, 4),  -- ADMINISTRATOR gets all
(2, 1), (2, 2),                   -- PROFESSOR gets READ, CREATE
(1, 1);                           -- STUDENT gets READ only
```

4. Run the application

---

## Tech Used

Java 17, Spring Boot 3, Spring Security 6, JWT (JJWT), JPA/Hibernate, MySQL, Lombok

---

## Testing with Postman

**Register:**
```json
POST http://localhost:8080/auth/register
{
  "username": "admin",
  "password": "123456",
  "roleRequest": "3"
}
```

**Login:**
```json
POST http://localhost:8080/auth/login
{
  "username": "admin",
  "password": "123456"
}
```

Copy the JWT token from the response and use it in the `Authorization` header for protected endpoints.