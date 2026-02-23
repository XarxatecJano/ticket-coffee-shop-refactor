package extras;

public class ShotExtra implements IExtraPricer {
    private static final double EXTRA_SHOT = 0.8;

    @Override
    public double getPrice(){
        return EXTRA_SHOT;
    }
}