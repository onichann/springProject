package com.wt.designPatterns.creationalPatterns.Factory.FactoryMethod;

public class BikeFactory implements CarFactory {
    @Override
    public Car getCar() {
        return new Bike();
    }
}
