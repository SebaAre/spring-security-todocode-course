# Spring Security Blog API

Final integrative exercise from the TodoCode Academy Spring Security course by Luisina de Paula.

**Academy Website:** [https://todocodeacademy.com/](https://todocodeacademy.com/)

---

## What it does

REST API with JWT authentication, role-based authorization, OAuth2 integration (Google & GitHub), and blog post management.

---

## Database Schema

**User:** id, username, email, password, enabled, roles (ManyToMany)

**Role:** id, name, permissions (ManyToMany)

**Permission:** id, name

**Post:** id, title, content, createdAt, updatedAt, author (ManyToOne)

**Author:** id, name, biography, email

---

## Endpoints

### Authentication
- `POST /auth/register` - Register new user (returns JWT)
- `POST /auth/login` - Login user (returns JWT)
- `GET /oauth2/authorization/google` - Login with Google
- `GET /oauth2/authorization/github` - Login with GitHub

### Posts
- `GET /posts` - Get all posts (public)
- `GET /posts/{id}` - Get post by ID (public)
- `POST /posts` - Create post (requires AUTHOR or ADMIN)
- `PUT /posts/{id}` - Update post (requires AUTHOR or ADMIN)
- `DELETE /posts/{id}` - Delete post (requires ADMIN)

---

## Setup

### Local Development

1. Create PostgreSQL database: `blog_security`

2. Edit `application.properties` or set environment variables:
```
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   JWT_SECRET=your_secret_key
```

3. Insert initial data (roles, permissions, author) in your database with queries

4. Run the application

### Docker Deployment
```bash
docker-compose build
docker-compose up
```

Access at: `http://localhost:8080`

---

## Tech Stack

Java 17, Spring Boot 3.5.7, Spring Security, PostgreSQL 16.11, JWT, OAuth2, Docker, Lombok, BCrypt

---

## Features

- JWT authentication with role-based authorization
- OAuth2 integration (Google & GitHub)
- Custom roles: USER, AUTHOR, ADMIN
- Permission-based access control
- BCrypt password encryption
- Docker containerization
- Method-level security with @PreAuthorize
