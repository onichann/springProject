package designPatterns.behavioralPatterns.Strategy;

public class ConcreteStrategy2 implements Strategy {
    @Override
    public void doSomething() {
        System.out.println("执行策略2");
    }
}
