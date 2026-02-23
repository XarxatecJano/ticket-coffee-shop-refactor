package pricing;

import domain.Size;

public class MuffinPricer implements IPricer {
    private static final double MUFFIN_PRICE = 2.2;

    @Override
    public double calculateBasePrice(Size size){
        return MUFFIN_PRICE;
    }
}