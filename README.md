# Cloud Architecture Demo

Centralized Enterprise Dependency Management Template and Core Application Service. This repository uses a Maven multi-module structure optimized for modern cloud-native deployment.

---

## 🛠️ Tech Stack & Prerequisites

Before building or running the application, ensure your local development environment meets the following requirements:

* **Java Development Kit (JDK):** Version 25 or higher
* **Build Tool:** Apache Maven 3.9+ (or use the included Maven wrapper)
* **Framework:** Spring Boot 4.1.0
* **Database:** H2 Database Engine (In-Memory / Runtime)

---

## 📁 Repository Structure

The project is organized as a Maven multi-module project:

```text
personal/
 ├── .githooks/               # Managed Git hooks for the project
 ├── core-application/        # Child Module: Core business logic and REST APIs
 │    ├── src/main/java/      # Application source code
 │    └── pom.xml             # Child module specific dependencies
 ├── .gitignore               # Global repository ignore rules
 ├── pom.xml                  # Parent BOM (Dependency & Plugin Management)
 └── README.md                # Project documentation (this file)

🗄️ Database Configuration
This application uses H2 for rapid development and testing.

Console Access: Once the application is running, access the H2 console at http://localhost:8080/h2-console.

Persistence: Currently configured as an in-memory database. All data is volatile and will be cleared upon application restart.

Schema Generation: The database schema is automatically generated from your Java Entities (@Entity) via Spring Data JPA.

🛡️ Error Handling & Validation
The application utilizes a GlobalExceptionHandler located in com.architecture.demo.exception. It provides consistent, standardized JSON error responses across the entire API.

Sample Error Response (400 Bad Request)
When validation fails (e.g., empty product name), the API returns:

JSON
{
    "timestamp": "2026-06-14T18:15:26.0285245",
    "status": 400,
    "errors": {
        "name": "Product name cannot be empty"
    }
}
🚀 Running the Application
Clone the repository:

Bash
git clone <your-repository-url>
Build the project:

Bash
mvn clean install
Run the core application:

Bash
cd core-application
mvn spring-boot:run
