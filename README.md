# Account Management Service

This is a demo project for an Account Management Service built with Java, Spring Boot, and H2 database.

## 🔧 Project Overview

The API allows creating, updating, retrieving and deleting simple account records. The architecture follows **layered clean architecture** principles, with clear separation between:

- `controller` — for HTTP handling
- `application` — for DTOs, mappers, and unified response models
- `domain` — for business logic and exceptions
- `infrastructure` — for database access and configuration

## ✅ Why this structure

The goal was to:

- Keep logic separated into clean, maintainable layers
- Use consistent API response format across all endpoints
- Avoid business logic in controller or persistence layers
- Apply best practices even in a small demo project

> I've also left developer comments in code to explain some decisions and clarify tradeoffs.

---

## 🚀 Running the project

Once the application is running locally (via `./gradlew bootRun`), you can access:

- **Main app** — http://localhost:8080
- **API base URL** — http://localhost:8080/api/v1
- **Swagger UI** — http://localhost:8080/swagger-ui/index.html
- **H2 Database Console** — http://localhost:8080/h2-console
  - Username: `sa`
  - Password: *(leave empty)*

---

## 📬 Postman Collection

A Postman file is included in the project, containing:
- Most use cases already pre-filled
- All core API requests ready to test
- Just download *LHV.postman_collection.json* and import into Postman

---

## 🔐 Environment & Secrets

All keys/secrets required for a production setup should be stored in `.env` files or secure secrets manager — never directly in config.
In my specific demo homework case - let it be... Sorry!

---

## 🙏 Final Note

Thank you for the opportunity to complete this assignment — it was a well-defined and thoughtful task.  
Compared to other take-home projects, your requirements were **very clear and specific**, which made the implementation experience great.

I appreciate the time you've taken to review this solution!

–  
**Sergei Eensalu**
