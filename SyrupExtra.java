public class SyrupExtra implements IExtraPricer {
    private static final double EXTRA_SYRUP = 0.5;

    @Override
    public double getPrice(){
        return EXTRA_SYRUP;
    }
}