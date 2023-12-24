## Parallelizing tasks in Spring Boot

* toUpperCaseSync - Synchronous execution: `http ':8080/upper/sync?inputs=a,b,c,d,e'`
* toUpperCaseWithCfOnCommonPool - Asynchronous execution with `CompletableFuture` on common
  pool: `http ':8080/upper/async1?inputs=a,b,c,d,e'`
* toUpperCaseWithCfOnSpringPool - Asynchronous execution with `CompletableFuture` on Spring
  pool: `http ':8080/upper/async2?inputs=a,b,c,d,e'`
* toUpperCaseWithSpringAsync - Asynchronous execution with `@Async` on Spring default
  pool: `http ':8080/upper/async3?inputs=a,b,c,d,e'`
* toUpperCaseWithSpringAsyncOnIoPool - Asynchronous execution with `@Async` on Spring custom pool. Requires setting
  `app.task.execution.io-pool.enabled` to `true`: `http ':8080/upper/async4?inputs=a,b,c,d,e'`