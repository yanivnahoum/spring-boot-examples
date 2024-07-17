## Context Propagation in Spring Boot MVC

To run this example use this test
class: [SomeServiceTest](src/test/java/com/att/training/springboot/examples/SomeServiceTest.java)

In [SomeService](src/main/java/com/att/training/springboot/examples/SomeService.java), we set the correlation id
as some contextual info that we'd like to propagate throughout the lifecycle of the current thread (which in Spring
MVC) typically is congruent with the current request lifecycle.
There are two methods of interest:

1. sync() - runs 2 tasks on the calling thread. Context is propagated perfectly.
1. async() - submits 2 tasks to a thread-pool. and waits for their completion. Context is **not** propagated.
   In order to solve the issue of context propagation in async executions, we can wrap every runnable submitted to
   our pool with a [decorator class](src/main/java/com/att/training/springboot/examples/ContextRunnable.java) that reads
   and stores the context upon creation, and then decorates the original task
   by setting the previously stored context on the current thread (that belongs to the pool).
   A more elegant way to do this is by defining a decorator on the thread-pool itself (as
   in [AsyncConfig](src/main/java/com/att/training/springboot/examples/AsyncConfig.java))

