package com.att.training.lib2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Welcomer {
    private final WelcomeProperties welcomeProperties;

    public String greet() {
        var stringBuilder = new StringBuilder();
        for (int i = 0; i < welcomeProperties.times(); ++i) {
            stringBuilder.append(welcomeProperties.message())
                    .append(" ");
        }
        return stringBuilder.toString();
    }
}
