package pricing;

import domain.Size;

public class CoffeePricer implements IPricer {
    
    private static final double COFFEE_S = 2.0;
    private static final double COFFEE_M = 2.5;
    private static final double COFFEE_L = 3.0;

    @Override
    public double calculateBasePrice(Size size){
        switch(size){
            case S: return COFFEE_S;
            case M: return COFFEE_M;
            case L: return COFFEE_L;
            default: throw new IllegalArgumentException("Unknown size: " + size);
        }
    }
}