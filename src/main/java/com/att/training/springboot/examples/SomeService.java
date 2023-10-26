package com.att.training.springboot.examples;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SomeService {
    private final Parent derived1;
    private final Derived2 derived2;

    String getDerived1Name() {
        return derived1.getName();
    }

    String getDerived2Name() {
        return derived2.getName();
    }
}

@Configuration(proxyBeanMethods = false)
//@Import(BootstrapConfig.class)
class Config1 {}


interface Parent {
    default String getName() {
        return getClass().getSimpleName();
    }
}

@Component
class Derived1 implements Parent {
    @Async
    public void foo() {
        System.out.println("foo");
    }
}

@Component
class Derived2 implements Parent {
    @Async
    public void bar() {
        System.out.println("bar");
    }
}
