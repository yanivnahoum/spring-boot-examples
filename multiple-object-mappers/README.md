## Multiple ObjectMappers

This app shows how to use multiple `ObjectMapper` instances in a Spring Boot application.
The default object mapper is used for serialization/deserialization performed
by the controllers, while a custom object mapper is used for specific cases.
The default mapper is configured to fail on unknown properties by setting the respective
property in the [`application.yaml`](src/main/resources/application.yml) file.
The custom mapper, defined [here](src/main/java/com/att/training/springboot/examples/JacksonConfig.java), is configured
to ignore unknown properties. To inject it into Spring beans,
the [`@Liberal`](src/main/java/com/att/training/springboot/examples/Liberal.java) qualifier is required.

Run the [UserControllerTest](src/test/java/com/att/training/springboot/examples/UserControllerTest.java) to see these
two mappers in action!
