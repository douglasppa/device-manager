## 📦 v0.1.0 – Initial setup, CRUD, and testing
**Date:** 2025-08-07

### 🚀 Features
- Created multi-module Maven project with `device-core`, `device-api`, and `device-persistence` modules.
- Defined `Device` domain model with validation rules and immutable fields.
- Implemented `DeviceEntity`, repository, and mappers for persistence.
- Configured PostgreSQL and JPA integration.
- Implemented REST endpoints for full device CRUD operations.
- Added API documentation with **SpringDoc OpenAPI 3** and **Swagger UI**.
- Documented DTOs and error responses with `@Schema` and custom examples.

### 🛠 Infrastructure
- Multistage Dockerfile for building and running the application.
- `docker-compose.yml` with services for API and PostgreSQL, using environment variables for sensitive configuration.
- Configured **JaCoCo** for code coverage reports (~90% achieved).

### 🧪 Tests
- Added unit tests for `DeviceService` and domain rules.
- Covered scenarios: valid device creation, full/partial updates, restrictions for `IN_USE` state, deletion with exception handling, immutability of `creationTime`, and prevention of name/brand changes when `IN_USE`.
- Tools: **JUnit 5** (`@Test`), **Mockito** (`@Mock`, `@InjectMocks`).

### 🔧 Other
- Prepared modular structure for future expansion.
