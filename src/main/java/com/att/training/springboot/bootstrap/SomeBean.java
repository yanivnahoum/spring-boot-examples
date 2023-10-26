package com.att.training.springboot.bootstrap;

import org.springframework.scheduling.annotation.Async;

public class SomeBean implements SomeInterface {
    @Async
    public void baz() {
        System.out.println("baz");
    }
}
