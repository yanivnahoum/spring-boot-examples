package com.att.training.springboot.examples.composition;

import lombok.RequiredArgsConstructor;

interface Engine {
    void start();

    void stop();
}

class GasolineEngine implements Engine {
    public void start() {
        // code to start a gasoline engine
    }

    public void stop() {
        // code to stop a gasoline engine
    }
}

class ElectricEngine implements Engine {
    public void start() {
        // code to start an electric engine
    }

    public void stop() {
        // code to stop an electric engine
    }
}

@RequiredArgsConstructor
class HybridEngine implements Engine {
    private final GasolineEngine gasolineEngine;
    private final ElectricEngine electricEngine;

    public void start() {
        // code to start a hybrid engine
    }

    public void stop() {
        // code to stop a hybrid engine
    }
}

class Brake {
    void apply() {
        // hit the brakes!
    }
}

@RequiredArgsConstructor
class Car {
    private final Engine engine;
    private final Brake brake;

    void start() {
        engine.start();
    }

    void stop() {
        engine.stop();
    }

    void brake() {
        brake.apply();
    }
}

class CarManufacturer {
    void buildCars() {
        Car tesla = new Car(new ElectricEngine(), new Brake());
        Car ford = new Car(new GasolineEngine(), new Brake());
        Car hybrid = new Car(new HybridEngine(new GasolineEngine(), new ElectricEngine()), new Brake());
    }
}
