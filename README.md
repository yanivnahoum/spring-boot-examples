## Oracle JDBC

1. Start the 2 oracle dbs: `docker compose up --detach`
2. Start the application
3. Run `http :8080/demo/users`

Note the jdbc url in the [application.yml](src/main/resources/application.yaml). It contains **multiple host:port pairs
**.
The Oracle XE image does not have an aarch64 version, and since I'm running on a mac with an Apple chip (aarch64) - I'm
running it using colima, with a network address. That's the reason for the 192.* ip in the jdbc url.
We can run `docker ps` and see the 2 containers running. If we change the names in the `users` table
we can figure out if we're accessing oracle1 or oracle2. We can then stop the db we're actively connected
(`docker stop db-oracle[1|2]-1`) to and see the switch-over to the second one.