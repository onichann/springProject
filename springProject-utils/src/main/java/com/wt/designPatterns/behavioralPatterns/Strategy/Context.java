package com.wt.designPatterns.behavioralPatterns.Strategy;

public class Context {
    private Strategy strategy;
    public Context(Strategy strategy){
        this.strategy=strategy;
    }
    public void execute(){
        strategy.doSomething();
    }
}