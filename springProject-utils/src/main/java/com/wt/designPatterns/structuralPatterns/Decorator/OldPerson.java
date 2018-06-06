package com.wt.designPatterns.structuralPatterns.Decorator;

public class OldPerson implements Person {
    @Override
    public void eat() {
        System.out.println("吃饭");
    }
}
