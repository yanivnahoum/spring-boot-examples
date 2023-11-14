package com.att.training.springboot.examples;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootExamplesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootExamplesApplication.class, args);
    }
}

@RestController
@RequestMapping("demo/api")
class DemoController {
    // http://a.com?a=b&c=5
    @GetMapping
    public Color get(@RequestParam Color color) {
        return color;
    }
}

@RequiredArgsConstructor
@Getter
enum Color {
    RED("red"),
    GREEN("green"),
    BLUE("blue"),
    UNKNOWN("unknown");

    private static final Map<String, Color> descriptionToType = Arrays.stream(values())
            .collect(toMap(Color::getDescription, Function.identity()));

    @JsonValue
    private final String description;

    public static Color fromDescription(String description) {
        return descriptionToType.getOrDefault(description, UNKNOWN);
    }
}

@Configuration(proxyBeanMethods = false)
class AppConfig {
    interface ColorConverter extends Converter<String, Color> {}
    @Bean
    ColorConverter colorConverter() {
        return Color::fromDescription;
    }
}

