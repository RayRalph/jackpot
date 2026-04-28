# Sporty Jackpot Service

A robust, enterprise-grade backend service designed to manage jackpot contributions and reward distributions. The system utilizes a decoupled strategy-based architecture, ensuring high extensibility for new jackpot mechanics.

## Architecture & Design Patterns
- **Strategy Pattern:** Reward and contribution logic is abstracted into strategies. New jackpot types can be added via the `StrategyFactory` without modifying core service logic.
- **Concurrency Management:** Built for high-contention environments using JPA `@Version` (Optimistic Locking) and Spring Retry annotations (`@Retryable`) to handle concurrent updates gracefully.
- **Resilient Service Layer:** Transactions are managed via `@Transactional` to ensure data integrity across multi-step jackpot evaluations.

## Technology Stack
- **Framework:** Spring Boot 4.0.0
- **Language:** Java 17
- **Database:** H2 (Embedded - configurable for Production)
- **API Documentation:** OpenAPI / Swagger 3.0.3
- **Messaging:** Kafka (Starter enabled)
- **Tooling:** Maven

## Assumptions
1. **Concurrency:** The system assumes that jackpot pool updates may happen simultaneously and relies on database-level versioning to ensure data consistency.
2. **Environment:** The application assumes a Unix-like environment for logging (specifically `/var/log/jackpot/`).
3. **Database:** Currently configured with H2. For production scaling, it is assumed this will be migrated to a persistent RDBMS like PostgreSQL or MariaDB.

## API Documentation
The API is fully documented via Swagger. Once the application is running, navigate to:
`http://localhost:8080/swagger-ui.html`

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+

### Build & Run
1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd jackpot

2. **Build the project:**
   ```bash
   mvn clean install

3. **Run the service:**
   ```bash
   mvn spring-boot:run


## Deployment Notes

## Troubleshooting
- **Build Errors:** If Maven dependency resolution fails, ensure your ~/.m2/repository is clean or force an update using mvn clean install -U.
- **Concurrency Errors:** If you encounter frequent ObjectOptimisticLockingFailureException in logs, verify that your retry logic is catching the correct exception type.

The application is configured to write logs to /var/log/jackpot/. 

**Important:** Ensure the application user has write permissions to this directory:

  ```bash
  sudo mkdir -p /var/log/jackpot
  sudo chown $USER:$USER /var/log/jackpot 
