## Structured Logging

This app demonstrates the use of the logstash-logback-encoder for structured logging.
The app uses MDC for request context, but also key-value pairs for message-related arguments.

MDC is handled using
a [HandlerInterceptor](src/main/java/com/att/training/springboot/examples/log/MdcHandlerInterceptor.java)
for setting and clearing some of the context info,
and [LogContext](src/main/java/com/att/training/springboot/examples/log/LogContext.java) to encapsulate all MDC related
actions.

Structured arguments per message are demonstrated by 3 alternatives:

1. Using Logstash's StructuredArguments as message arguments
2. Using Slf4j's Markers
3. Using Slf4j's KeyValue Pairs

Run the app via and send one of the following requests:

* `http :8080/app/users/1` - happy flow
* `http :8080/app/users/4` - unhappy flow
