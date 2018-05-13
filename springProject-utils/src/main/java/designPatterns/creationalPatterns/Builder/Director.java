package designPatterns.creationalPatterns.Builder;

public class Director {
    private Builder builder;
    public Director(Builder builder){
        this.builder=builder;
    }
    public void builderProduct(){
        builder.buildName();
        builder.buildClothes();
        builder.buildWuqi();
    }
}
