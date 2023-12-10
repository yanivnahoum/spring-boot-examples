## Parallelizing tasks in Spring Boot

* toUpperCaseSync - Synchronous execution
* toUpperCaseWithCfOnCommonPool - Asynchronous execution with `CompletableFuture` on common pool
* toUpperCaseWithCfOnSpringPool - Asynchronous execution with `CompletableFuture` on Spring pool
* toUpperCaseWithSpringAsync - Asynchronous execution with `@Async` on Spring default pool
* toUpperCaseWithSpringAsyncOnIoPool - Asynchronous execution with `@Async` on Spring custom pool. Requires setting
`app.task.execution.io-pool.enabled` to `true`