# Azure Cosmos DB Spring Boot Example

This module demonstrates how to build a production-ready Spring Boot application that interacts with **Azure Cosmos DB
** (SQL API).

## Features

- **Spring Data Cosmos**: Uses the official `spring-cloud-azure-starter-data-cosmos`.
- **Entity Modeling**: Demonstrates `@Container`, `@PartitionKey`, and optimistic locking with `@Version`.
- **Testcontainers**: Integration tests run against the **Azure Cosmos DB Emulator** using Testcontainers.
- **REST API**: Simple CRUD operations for `TodoItem` entities.

## Prerequisites

- Java 21+
- Docker (for running tests with Testcontainers)

## Configuration

The application is configured in `src/main/resources/application.yml`.
By default, it attempts to connect to a local Cosmos DB Emulator on `https://localhost:8081`.

### Environment Variables

You can override settings with environment variables:

| Variable          | Description                                                                            |
|-------------------|----------------------------------------------------------------------------------------|
| `COSMOS_ENDPOINT` | The URI of your Cosmos DB account (e.g., `https://my-cosmos.documents.azure.com:443/`) |
| `COSMOS_KEY`      | The primary key for your Cosmos DB account                                             |

## Running Locally

1. **Start Cosmos DB Emulator** (if not using Azure):
   You can run the emulator via Docker:
   ```bash
   docker run -p 8081:8081 -p 10251-10255:10251-10255 -e AZURE_COSMOS_EMULATOR_PARTITION_COUNT=3 -e AZURE_COSMOS_EMULATOR_ENABLE_DATA_PERSISTENCE=false mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator:latest
   ```
   *Note: On Apple Silicon (M1/M2/M3), the emulator can be slow or unstable. Ensure you allocated enough memory to
   Docker.*

2. **Run the App**:
   ```bash
   ./mvnw spring-boot:run -pl azure-cosmos-db
   ```

3. **Interact with API**:
   ```bash
   # Create
   curl -X POST -H "Content-Type: application/json" -d '{"userId":"alice", "description":"Buy milk"}' http://localhost:8080/api/todos

   # List
   curl "http://localhost:8080/api/todos?userId=alice"
   ```

## Testing

The module uses `testcontainers-java` to spin up a Cosmos DB Emulator for integration tests.

To run tests:

```bash
./mvnw test -pl azure-cosmos-db
```

### Note on Testcontainers & SSL

The test setup (`AzureCosmosDbApplicationTests.java`) automatically creates a temporary Java KeyStore including the
emulator's self-signed certificate and configures the `javax.net.ssl.trustStore` system properties. This allows the
Spring Boot application to connect to the containerized emulator securely without manual certificate installation.
