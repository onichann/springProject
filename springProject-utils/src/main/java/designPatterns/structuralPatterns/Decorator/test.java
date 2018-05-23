package designPatterns.structuralPatterns.Decorator;

public class test {
    public static void main(String[] args) {
        new NewPerson(new OldPerson()).eat();
    }
}
