package com.att.training.springboot.examples.inheritance;

abstract class BaseCar {
    // internal state
    public abstract void start();

    public abstract void stop();

    public void brake() {
        // apply brakes
    }

    abstract void energize();
}

class ElectricCar extends BaseCar {

    @Override
    public void start() {
        // start the electric engine
    }

    @Override
    public void stop() {
        // stop the electric engine
    }

    @Override
    public void energize() {
        // charge the battery
    }
}

class GasolineCar extends BaseCar {

    @Override
    public void start() {
        // start the gasoline engine
    }

    @Override
    public void stop() {
        // stop the gasoline engine
    }

    @Override
    public void energize() {
        // Fill the tank
    }
}

class CarManufacturer {
    void buildCars() {
        ElectricCar tesla = new ElectricCar();
        GasolineCar ford = new GasolineCar();

        goForARide(tesla);
        goForARide(ford);
    }

    void goForARide(BaseCar car) {
        car.start();
        car.brake();
        car.stop();
        car.energize();
    }
}
