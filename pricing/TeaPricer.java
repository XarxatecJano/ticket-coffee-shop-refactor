package pricing;

import domain.Size; 
import pricing.IPricer;

public class TeaPricer implements IPricer {
    private static final double TEA_S = 1.5;
    private static final double TEA_M = 2.0;
    private static final double TEA_L = 2.3;

    @Override
    public double calculateBasePrice(Size size) {
        switch(size){
            case S: return TEA_S;
            case M: return TEA_M;
            case L: return TEA_L;
            default: throw new IllegalArgumentException("Unknown size: " + size);
        }
    }
}