## Oracle JDBC

1. Start the 2 oracle dbs from [here](db/compose.yaml): `docker compose up --detach`
2. Start the [application](src/main/java/com/att/training/springboot/examples/oracle/OracleJdbcApplication.java)
3. Run `http :8080/demo/users`

Note the jdbc url in the [application.yml](src/main/resources/application.yaml). It contains **multiple** host:port
pairs.
We can run `docker ps` and see the 2 containers running. If we change the names in the `users` table
we can figure out if we're accessing oracle1 or oracle2. We can then stop the db we're actively connected
(`docker stop db-oracle[1|2]-1`) to and see the switch-over to the second one.
