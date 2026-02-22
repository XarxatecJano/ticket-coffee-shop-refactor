
public final class Pricers {
    private static IPricer createProduct(String product){
        switch(product){
            case "coffee" : return new CoffeePricer();
            case "tea" : return new TeaPricer();
            case "muffin" : return new MuffinPricer();
            default : throw new IllegalArgumentException("Unknown product: " + product);
        }
    }

    public static IPricer forProduct(String product){
        return createProduct(product);
    }
}