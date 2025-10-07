# 🛒 Spring Store API

This repository hosts a robust **RESTful API** for an E-commerce store backend (Mosh Spring Boot course), developed using the **Spring Boot** framework.

The project demonstrates proficiency in building secure, transactional, and well-documented microservices, covering everything from data persistence and migration to security (JWT) and external payment integration (Stripe).

---

## 🛠️ Tech Stack & Key Features

This project utilizes a modern, enterprise-ready technology stack. Recruiters can view this section to quickly assess project complexity and demonstrated skills.

| Category | Technology | Key Skill Demonstrated |
| :--- | :--- | :--- |
| **Backend Core** | **Spring Boot 3.4.1** & **Java 21** | Building a standalone, production-ready microservice with the latest standards. |
| **Data Persistence** | **Spring Data JPA** & **MySQL** | Implementing **CRUD** operations and relational database mapping (Hibernate). |
| **Database Migration** | **Flyway** | Managing and versioning database schema changes reliably across environments. |
| **Security** | **Spring Security** & **JWT** | Implementing token-based authentication (JSON Web Tokens) for securing API endpoints. |
| **Payment Integration** | **Stripe** | Integration with a third-party payment gateway for processing transactions. |
| **API Documentation** | **Springdoc OpenAPI (Swagger)** | Auto-generating comprehensive API documentation for easy consumption by frontend teams. |
| **Code Utility** | **Project Lombok** & **MapStruct** | Reducing boilerplate code and implementing efficient Data Transfer Object (DTO) mapping. |

---

## 🚀 Live Application and Documentation

The API is currently deployed and hosted on **Railway**, available for testing and review.

### 🌐 Live Application Base URL

> **URL:** `https://spring-store-api-production-4251.up.railway.app`

### 📄 Swagger UI (Interactive API Documentation)

Navigate here to view all endpoints, request/response models, and execute live API calls against the deployed service.

> **URL:** `https://spring-store-api-production-4251.up.railway.app/swagger-ui/index.html`

### 📦 Postman Collection

A Postman collection is available in the repository root for streamlined testing and integration.

* **Key Requests:** Includes requests for User Registration, Login (to generate JWT), Product Management, and Checkout.
* **Testing:** **Postman Test Scripts** are included to validate response codes and data structure, demonstrating automated API testing skills.

---

## ⚙️ Local Development Setup

### Prerequisites

* **Java Development Kit (JDK) 21**
* **Maven**
* **MySQL Database** instance

### Steps to Run Locally

1.  **Clone and Navigate:**
    ```bash
    git clone [https://github.com/xaviermaldonadony/spring-store-api.git](https://github.com/xaviermaldonadony/spring-store-api.git)
    cd spring-store-api
    ```
2.  **Configure Database:**
    * Ensure your **MySQL** server is running.
    * Update the database credentials in your `application.properties` (or set up a local `.env` file using the included `.env.example`).
3.  **Run Flyway Migration (Optional):**
    * To apply the schema changes and initial seed data, execute the Flyway plugin:
        ```bash
        ./mvnw flyway:migrate
        ```
4.  **Start the Application:**
    ```bash
    ./mvnw spring-boot:run
    ```
    The application will be accessible at `http://localhost:8080`.