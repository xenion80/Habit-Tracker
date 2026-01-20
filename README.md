
# Habit Tracker API (Spring Boot)

A backend REST API for tracking habits, categories, and tasks with **clean domain modeling**, **ownership enforcement**, **soft deletion**, and **proper HTTP semantics**.

This project is built as a **learning-focused backend built with production-grade design principles**, emphasizing correctness, invariants, and maintainability over feature sprawl.

---
## This project is intended for:
- students learning backend engineering
- developers exploring domain-driven design in Spring Boot
- contributors interested in clean API semantics
---

## 🚀 Features

* User-based habit tracking
* Categories and tasks under users
* Habit completion with streak tracking
* Daily habit logs
* **PATCH-based updates (no setter abuse)**
* **Soft delete** for habits, categories, and tasks
* **Ownership checks** for all protected resources
* Global exception handling with correct HTTP status codes
* Pagination, sorting, and filtering
* DTO-based API responses
  
> **Note: User ownership is currently enforced at the service layer; authentication is planned.**


---
## 📡 API Overview

This API exposes endpoints for managing users, habits, categories, and tasks.

Key operations include:
- Creating and managing habits per user
- Daily habit completion with streak tracking
- Categorization of tasks
- Soft deletion and PATCH-based updates
- Ownership-enforced access to all resources

For a full list of endpoints and request examples, see:
👉 [API Documentation](./API.md)
---

## 🧠 Design Principles Used

* **Domain-driven mutations**
  All state changes happen through domain methods (e.g. `completeToday()`, `rename()`).

* **No blind setters**
  Entities expose behavior, not raw mutation.

* **Soft delete over hard delete**
  Deleted records are hidden, not removed.

* **Clear responsibility separation**

  * Controllers → request/response only
  * Services → authorization & orchestration
  * Entities → business rules
  * Repositories → data access only

* **Correct HTTP semantics**

  * `200 OK`, `201 Created`
  * `204 No Content`
  * `400 Bad Request`
  * `403 Forbidden`
  * `404 Not Found`
  * `409 Conflict`

---

## 🛠️ Tech Stack

* Java 21
* Spring Boot (Web, Data JPA, Actuator)
* Spring Data JPA
* Hibernate
* PostgreSQL / MySQL (configurable)
* Lombok
* Spring Boot Actuator

---

## 📦 Project Structure (Simplified)

```
com.example.habittracker
├── controllers
├── services
├── entities
├── repositories
├── dtos
├── inputmodels
├── exception
└── advices
```

---

## 🔐 Ownership Model

All user-owned resources (habits, categories, tasks) enforce ownership at the **service layer**.

If a user tries to access or modify a resource they do not own:

* The request fails with **403 Forbidden**
* The resource is never leaked

---

## 🧹 Soft Delete Strategy

Instead of deleting rows from the database:

* Entities contain a `deletedAt` timestamp
* Queries filter out deleted records
* Domain rules prevent operations on deleted entities

Example:

* Renaming a deleted habit → `409 Conflict`
* Fetching a deleted habit → `404 Not Found`

---

## 🧪 Error Handling

Centralized via `@RestControllerAdvice`.

Examples:

* Validation errors → `400`
* Not found → `404`
* Ownership violation → `403`
* Rule violation (double completion, rename deleted) → `409`

All errors return a consistent JSON structure.

---

## ❤️ Health Check

The application exposes a health endpoint using Spring Boot Actuator:

```
GET /actuator/health
```

Used for deployment and operational checks.

---

## ▶️ Running Locally

### Prerequisites

* Java 17+
* Maven
* PostgreSQL / MySQL

### Steps

1. Clone the repository
2. Configure database credentials via environment variables or `application.properties`
3. Run:

   ```
   mvn spring-boot:run
   ```
4. Access API at:

   ```
   http://localhost:8080
   ```

---

## 🌍 Live Deployment

The API is deployed on Render:

https://habit-tracker-ryua.onrender.com

---

## 📮 API Testing

The API is tested using **Postman** during development.
Key flows tested:

* Happy paths
* Ownership violations
* Validation failures
* Soft delete behavior
* Domain rule enforcement

---

## 📌 Current Status

* ✅ Core domain complete
* ✅ Ownership checks implemented
* ✅ Soft delete implemented
* ✅ PATCH semantics applied
* ⏳ Authentication (planned)
* ⏳ Integration tests (planned)

---

## 🎯 Learning Goals of This Project

This project was built to deeply understand:

* How real backends enforce correctness
* Why domain rules matter
* How to design APIs that are hard to misuse
* How to structure Spring Boot applications cleanly

---


## 📜 License

This project is licensed under the MIT License.
See LICENSE.md(./license.md) for details.


---
## 👨‍💻 Author

### Karan Sardar

- GitHub: https://github.com/xenion80
- Linkedin: https://www.linkedin.com/in/karan-sardar-1521772b4/
---
## ⭐ Support

* If you found this project helpful, consider giving it a ⭐️.
* It helps others discover the project and motivates further improvements.

---
## 📧 Contact

Have questions? Feel free to reach out:
- Email: snsardarkaran94@gmail.com
- Open an issue: [Issues](https://github.com/xenion80/habit-tracker/issues)
  
<div align="center">

**Built with ❤️ using Spring Boot • Open Source Friendly💜**
</div>
