package extras;

public class MilkExtra implements IExtraPricer {
    private static final double EXTRA_MILK = 0.2;

    @Override
    public double getPrice(){
        return EXTRA_MILK;
    }
}