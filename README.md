# AutoSphere - E-Commerce Platform

A full-stack e-commerce web application built with Spring Boot, using cars as the
product catalog. Enables users to browse, search, and purchase products through a
secure, role-based platform.

## Tech Stack
- Java
- Spring Framework & Spring Boot
- Spring Security
- Spring Data JPA & Hibernate
- MySQL

## Features
- Product browsing and search functionality
- Database schema and entity relationships designed for users, products, orders, and cart
- User authentication and role-based access control via Spring Security
- MVC architecture with clear separation of controllers, services, and repositories

## Architecture
The application follows the MVC (Model-View-Controller) pattern:
- **Controllers** — handle HTTP requests and route to appropriate services
- **Services** — contain business logic
- **Repositories** — handle data persistence via Spring Data JPA

## How to Run
1. Clone the repository
   ```bash
   git clone https://github.com/Ishaan45-ux/Autosphere
   ```
2. Configure your MySQL database credentials in `application.properties`
3. Build and run the application
   ```bash
   mvn spring-boot:run
   ```
4. Access the application at `http://localhost:8080`

## Status
Actively maintained. Core catalog, cart, and authentication features are implemented.

## Author
Ishaan Ambare
