package pricing;

import domain.ProductType;
import pricing.IPricer;

public final class Pricers {
    private static IPricer createProduct(ProductType product){
        switch(product){
            case COFFEE : return new CoffeePricer();
            case TEA : return new TeaPricer();
            case MUFFIN : return new MuffinPricer();
            default : throw new IllegalArgumentException("Unknown product: " + product);
        }
    }

    public static IPricer forProduct(ProductType product){
        return createProduct(product);
    }
}