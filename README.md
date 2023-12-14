## Spring Cache With Redis

### Running the application

1. Run Redis locally: `./redis`
2. Optionally connect your desktop client to Redis @ localhost:6379, password=dummy
3. Run the application locally
4. http requests:

* Fetch user1: `http :8080/demo/users/1`
* Fetch user1 again: `http :8080/demo/users/1` - notice the cache hit (no db access)
* Update user1: `http PUT :8080/demo/users id=1 name=aaa`
* Delete user 1: `http DELETE :8080/demo/users/1`

5. Stop Redis: `./redis stop`

### Testing the application

Run [SpringBootExamplesApplicationTest](src/test/java/com/att/training/springboot/examples/SpringBootExamplesApplicationTest.java)