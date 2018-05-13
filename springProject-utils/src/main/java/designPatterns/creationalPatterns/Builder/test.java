package designPatterns.creationalPatterns.Builder;

public class test {

    public static void main(String[] args) {
        Builder builder=new CharacterABuilder();
        Director director=new Director(builder);
        director.builderProduct();
        Product product=builder.create();
        System.out.println("product = " + product.toString());
    }
}
