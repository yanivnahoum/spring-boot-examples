## Spring Boot Properties

This repo shows how to map configuration properties via spring's property sources:

1. application.yaml
2. Program arguments:
   `java -jar spring-properties/target/spring-properties-0.0.1-SNAPSHOT.jar --app.order-client.response-timeout=20s`
3. System properties:
   `java -Dapp.order-client.response-timeout=60s -jar spring-properties/target/spring-properties-0.0.1-SNAPSHOT.jar`
4. Environment
   variables:
   `APP_ORDER_CLIENT_BASE_URL=http://abc.com java -jar spring-properties/target/spring-properties-0.0.1-SNAPSHOT.jar`
5. Config trees: uncomment the `spring.config.import` in `application.yaml` to see the effect of config trees