package pricing;

import domain.Size;

public class MuffinPricer implements IPricer {

    private static final double MUFFIN_PRICE_S = 2.2;
    private static final double MUFFIN_PRICE_M = 2.2;
    private static final double MUFFIN_PRICE_L = 2.2;

    @Override
    public double calculateBasePrice(Size size){
        switch(size){
            case S: return MUFFIN_PRICE_S;
            case M: return MUFFIN_PRICE_M;
            case L: return MUFFIN_PRICE_L;
            default: throw new IllegalArgumentException("Unknown size: " + size);
        }
    }
}