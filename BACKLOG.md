## 📌 Project Backlog – Device Manager

This document contains planned improvements, technical enhancements, and ideas for future evolution of the **Device Manager** project.

---

### ⚡ Quick Wins
Potential features or improvements that can be implemented in ~1h during a technical interview:
- [ ] API versioning (e.g., `/api/v1/devices`).
- [ ] Configure separate Spring profiles (`dev`, `test`) in `application.yml` with different DB settings.
- [ ] Add CORS configuration for controlled frontend access.
- [ ] Implement basic security headers using Spring Security.
- [ ] Improve Swagger documentation with more examples and descriptions.
- [ ] Add pagination support to the device listing endpoint.
- [ ] Add sorting support to the device listing endpoint.
- [ ] Create a `/health` endpoint with Spring Boot Actuator.
- [ ] Return standardized error responses using `@ControllerAdvice`.

---

### 🚀 Planned Features
- [ ] Implement search and filtering for devices by multiple parameters (name, brand, state).
- [ ] Add endpoint to change device state with proper validation and history tracking.
- [ ] Implement soft-delete for devices, preserving historical data.
- [ ] Create integration tests for the REST API using Testcontainers.
- [ ] Add API error codes and standardized error response format.
- [ ] Implement user authentication and role-based access control (RBAC).
- [ ] Provide API client SDK generation from OpenAPI spec.
- [ ] Add support for exporting device data in CSV and JSON formats.
- [ ] Enable WebSocket notifications for device status changes.

---

### 🛠 Infrastructure Improvements
- [ ] Implement rate limiting for API endpoints.
- [ ] Use structured logging (JSON format) with Logback or SLF4J.
- [ ] Set up a GitHub Actions CI/CD pipeline with:
    - Build and test stages
    - Static analysis with Checkstyle or Spotless
    - Code quality and coverage analysis via SonarCloud.
- [ ] Add Spring Boot Actuator for application info and metrics exposure.
- [ ] Expose application metrics to Prometheus and visualize in Grafana dashboards.

---

### 💡 Ideas
- [ ] Create a frontend UI for device management using React or Angular.
