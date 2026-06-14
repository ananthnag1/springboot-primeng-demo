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
