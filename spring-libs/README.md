## Spring Boot Examples

### Creating and Consuming Spring Libraries

This project contains a simple Spring Boot application (app1) that consumes 2 libraries (lib1 and lib2).

* Lib1 - provides auto-configuration via the `org.springframework.boot.autoconfigure.AutoConfiguration.imports`
  mechanism.
* Lib2 - provides no special configuration, and the consuming app needs to explicitly add its components to the
  Spring context
