package com.att.training.springboot.examples.user;

import com.att.training.springboot.examples.log.LogContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.Markers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.util.Map.entry;
import static net.logstash.logback.argument.StructuredArguments.defer;
import static net.logstash.logback.argument.StructuredArguments.entries;
import static net.logstash.logback.argument.StructuredArguments.kv;
import static net.logstash.logback.marker.Markers.append;
import static net.logstash.logback.marker.Markers.appendEntries;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    User getById(@PathVariable int id) {
        LogContext.setOperationName("get-user");
        log.info("#getById - Attempt to fetch user {}", id);
        var user = userService.getById(id);

        // Structured logging using logstash's StructuredArguments
        log.info("#getById - Found user!", kv("userId", user.id()), kv("username", user.name()));
        var args = Map.ofEntries(
                entry("userId", user.id()),
                entry("username", user.name())
        );
        log.info("#getById - Found user!", entries(args));
        log.info("#getById - Found user!", defer(() -> entries(args)));
        // Send a POJO as a value:
        log.info("#getById - Found user!", kv("user", user));
        // This is only possible with StructuredArguments
        log.info("#getById - Found user with {}, {}", kv("userId", user.id()), kv("username", user.name()));

        // Structured logging using markers
        log.info(append("userId", user.id())
                        .and(append("username", user.name())),
                "#getById - Found user!");
        log.info(appendEntries(args), "#getById - Found user!");
        log.info(Markers.defer(() -> appendEntries(args)), "#getById - Found user!");
        // Send a POJO as a value:
        log.info(append("user", user), "#getById - Found user!");
        return user;
    }
}
