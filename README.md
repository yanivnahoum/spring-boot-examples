1. Build with maven `./mvnw verify` or with gradle `./gradlew build`
2. Observe [test](src/test/java/com/att/training/springboot/examples/AppWithConfigTreeTest.java) failure with the
   following message:
   org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from
   type [org.springframework.boot.env.ConfigTreePropertySource$PropertyFileContent] to
   type [com.att.training.springboot.examples.FullName]
3. Change Spring Boot version to 3.1.6 - test passes.