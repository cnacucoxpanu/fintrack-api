# FinTrack API

FinTrack is a RESTful API application built with Spring Boot for tracking financial categories. This project demonstrates clean architecture, DTO usage, and adherence to SOLID principles.

## 🚀 Features

*   **Category Management**: Create, Read, Update, and Delete (CRUD) operations for financial categories.
*   **Search**: Filter categories by name using query parameters.
*   **Validation**: Data integrity checks using Jakarta Validation (JSR-380).
*   **Architecture**: Layered architecture (Controller -> Service -> Repository).

## 🛠 Tech Stack

*   **Java 17**
*   **Spring Boot 3.x** (Web, Data JPA, Validation)
*   **H2 Database** (In-memory database for testing)
*   **Lombok** (Boilerplate reduction)
*   **Gradle** (Build tool)

## 📂 Project Structure

The project follows standard Spring Boot directory structure:

*   `controller`: Handles HTTP requests.
*   `service`: Contains business logic.
*   `repository`: Data access layer (Spring Data JPA).
*   `entity`: Database models.
*   `dto`: Data Transfer Objects for API requests/responses.
*   `mapper`: Utilities to convert between Entity and DTO.

## 🔌 API Endpoints

### Categories

| Method | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/api/categories` | Get all categories (optional `?name=` filter) |
| `GET` | `/api/categories/{id}` | Get a specific category by ID |
| `POST` | `/api/categories` | Create a new category |
| `DELETE` | `/api/categories/{id}` | Delete a category by ID |
