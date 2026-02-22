public class MuffinPricer implements IPricer {
    private static final double MUFFIN_PRICE = 2.2;

    @Override
    public double calculateBasePrice(String size, boolean happyHour){
        return MUFFIN_PRICE;
    }
}