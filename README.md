## Spring Boot Examples

This repo contains multiple branches, each an isolated Spring Boot app demonstrating the use or implementation of a
different concern.

**Branches**:

* abstract-datasource: dynamic routing of multiple data sources
* structured-logging: structured logging using logback and a json encoder
* error-handling: comparing various error handling patterns
* composition-vs-inheritance: comparing 2 software design patterns: composition vs inheritance
* feature-management: using Azure App Configuration to manage feature flags
* spring-libs: creating and consuming Spring libraries
* spring-properties: defining and using Spring Boot properties
* context-propagation: propagating thread-local context to async executions in Spring MVC
* spring-converters: creating and using Spring converters
* spring-concurrency: Parallelizing tasks using Spring's TaskExecutor, Async and CompletableFutures
* http-clients: using and testing various Spring http clients
* enable-preview: a simple project using java 21 with preview features enabled for prod and test code
* misc-topics: a few small and useful solutions to common use-cases:
    * How to avoid deadlocks when async tasks spawn additional async tasks
    * Stream API: using the element and its index
    * Stream API: Distinct with key
* spring-cache-redis - Using spring-cache backed by Redis
* spring-boot-2.7 - Resolving SCA issues in Spring Boot 2.7.18
