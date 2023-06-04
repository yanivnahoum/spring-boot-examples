## Using Multiple DataSources Dynamically in Spring Boot

This app demonstrates the implementation of a dynamic datasource routing.
Given a request, the app determines which datasource it should be bound to,
and sets the datasource key on the current thread's context.
A dynamic routing datasource is configured on startup. This datasource is capable of extracting the datasource key
from the current thread's context, retrieving the matching datasource, and using it.
Any Spring feature requiring a datasource (e.g. `@Transactional`, `JdbcTemplate`) will work with it transparently.

The app uses 2 postgreSQL databases - east and west, with the same [schema](src/main/resources/schema.sql).
See the [data file](src/main/resources/data.sql) for the initial data.

### Run the example:

1. Start the 2 containerized databases by cd'ing into the [db](db) directory and running: `docker compose up --detach`
2. Run the app (main class
   is [SpringBootExamplesApplication](src/main/java/com/att/training/springboot/examples/SpringBootExamplesApplication.java))
3. Make an HTTP request to insert a record into the **east** db: `http :8080/app/users id=10 name=Alice`
4. Make an HTTP request to insert a record into the **west** db: `http :8080/app/users id=110 name=Bob`
5. Make an HTTP request to insert a record into the **default** (east) db: `http :8080/app/users id=210 name=Carl`
6. Make an HTTP request to insert a **non-unique id into east**: `http :8080/app/users id=1 name=Bobby` - this will
   result in an exception and the transaction will be **rolled back**.
7. When you're done, run the following from the [db](db) directory: `docker compose down`

