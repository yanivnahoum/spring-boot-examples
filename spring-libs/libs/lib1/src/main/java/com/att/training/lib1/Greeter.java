package com.att.training.lib1;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Greeter {
    private final GreetingProperties greetingProperties;

    public String greet() {
        var stringBuilder = new StringBuilder();
        for (int i = 0; i < greetingProperties.times(); ++i) {
            stringBuilder.append(greetingProperties.message())
                    .append(" ");
        }
        return stringBuilder.toString();
    }
}
