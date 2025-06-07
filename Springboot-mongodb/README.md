# 📝 Task Management Service

A Spring Boot service for managing tasks, integrated with **MongoDB**. The application supports full CRUD operations, search, pagination, and custom aggregations.

---

## 🚀 Features

- Add, update, delete, and fetch tasks
- Search tasks by:
  - ID
  - Severity
  - Description (full-text search)
  - Assignee or Subtasks (using MongoDB `$search`)
- Pagination support for task listings
- Sample data generation for testing

---

## 🧱 Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Data MongoDB**
- **MongoDB (local or cloud)**
- **Lombok**
- **Embedded MongoDB (Flapdoodle) for integration testing**
- **JUnit 5 / Mockito**

---

## 📦 Getting Started

### ✅ Prerequisites

- Java 17+
- Maven 3.6+
- MongoDB (running locally or use MongoDB Atlas)

### ▶️ Run the application

```bash
mvn spring-boot:run
