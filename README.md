# Device Manager

![GitHub tag](https://img.shields.io/github/v/tag/douglasppa/device-manager)

**Device Manager** is a modular backend project built with **Java 21**, **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**, designed for maintainability and future scalability.
It implements a complete REST API for device management, with interactive API documentation via Swagger UI and unit tests ensuring high code coverage.

---

## 🌟 Objectives

Demonstrate how to structure and implement a modern, scalable Java project, covering:

- 🏗️ Multi-module architecture with clear separation of concerns:
  - device-core → domain model and business rules
  - device-persistence → persistence layer and repositories
  - device-api → REST controllers, DTOs, and API documentation
- 📦 Full CRUD for devices
- 📄 Automatic API documentation using SpringDoc OpenAPI 3 + Swagger UI
- 🗄️ Integration with PostgreSQL using Spring Data JPA
- 🐳 Multi-stage Dockerfile and docker-compose.yml for local development
- 🧪 Unit tests with JUnit 5 and Mockito (~90% coverage with JaCoCo)
- ⚙️ Clean, extensible configuration for future growth (new modules, endpoints, databases)

---

## 🧰 Technologies

- [Java 21](https://openjdk.org/projects/jdk/21/)
- [Maven](https://maven.apache.org/download.cgi)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL](https://www.postgresql.org/)
- [SpringDoc OpenAPI 3](https://springdoc.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [JaCoCo](https://www.jacoco.org/)
- [Docker & Docker Compose](https://docs.docker.com/compose/)

---

## 🚀 Running the Project Locally

### Prerequisites

- [Docker + Docker Compose](https://www.docker.com/)
[Git](https://git-scm.com/)
- Java 21+ (optional, for local dev)
- Maven 3.9+ (optional, for local dev)
- IntelliJ IDEA Community Edition (suggested)

### Quick Start (via Docker Compose)

```bash
git clone https://github.com/douglasppa/device-manager.git
cd device-manager
docker-compose up -d --build

```

---

## 📬 API Endpoints
| Method | Path            | Description               |
|--------| ----------------|---------------------------|
| GET    | `/devices`      | List all devices          |
| GET    | `/devices/{id}` | Retrieve a device by ID   |
| POST   | `/devices`      | Create a new device       |
| PUT    | `/devices/{id}` | Fully update a device     |
| PATCH  | `/devices/{id}` | Partially update a device |
| DELETE | `/devices/{id}` | Delete a device           |

🔗 APIs available at: http://localhost:8080/swagger-ui.html

---

## 🧪 Tests and Coverage

### Run tests and generate JaCoCo report:

```bash
mvn clean verify
```
🔗 HTML report available at: target/site/jacoco/index.html

---

## 🧹 Code Style and Linting

### This project uses Checkstyle and optionally Spotless to keep the codebase consistent:
```bash
# Apply automatic formatting
mvn spotless:check
mvn spotless:apply

# Run static analysis
mvn checkstyle:check
```
---

## 🔁 Project Structure (Modules)
```bash
device-manager/
├── device-core/         # Domain, entities, and business rules
├── device-persistence/  # JPA repositories and persistence entities
├── device-api/          # REST Controllers, DTOs, mappers, config
├── docker-compose.yml   # API + PostgreSQL for local development
├── Dockerfile           # Multi-stage build and run
└── README.md, CHANGELOG.md, pom.xml
```

## 💡 Design Decisions

### 1. Multi-module Maven architecture
Splitting the project into core, persistence, and api modules improves maintainability, testability, and scalability.
- Core contains pure business logic without dependencies on external frameworks.
- Persistence handles all database-related logic and JPA configuration.
- API focuses solely on HTTP handling, validation, and serialization.

### 2. Domain-Driven approach
The Device domain model enforces business rules directly in its methods (isInUse(), canBeDeleted()), ensuring consistency regardless of the persistence or API layers.

### 3. DTO and Mapper separation
- Entity ↔ Domain mapping is isolated in DeviceMapper.
- Domain ↔ DTO mapping is handled by DeviceDtoMapper.
This prevents leaking persistence details into the API layer and makes migrations easier.

### 4. SpringDoc OpenAPI integration
Using SpringDoc OpenAPI ensures the documentation is always in sync with the code, with minimal manual maintenance.

### 5. Dockerized development
The project is containerized with a multi-stage Docker build for smaller production images and a docker-compose.yml for local PostgreSQL setup.

### 6. Testing strategy
Unit tests target both domain logic and service layers with JUnit 5 + Mockito, while JaCoCo ensures coverage visibility. The current configuration achieves ~90% coverage.

---

🤝 Contributions
This project is primarily for learning purposes, but feedback and contributions are welcome!
Feel free to open an issue or submit a pull request 🚀