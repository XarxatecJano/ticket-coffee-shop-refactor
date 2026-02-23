public class CoffeePricer implements IPricer {
    private static final double HAPPY_HOUR_DISCOUNT = 0.20;
  
    private static final double COFFEE_S = 2.0;
    private static final double COFFEE_M = 2.5;
    private static final double COFFEE_L = 3.0;

    @Override
    public double calculateBasePrice(String size){
        double price;
        if(size.equals("S")){
            return COFFEE_S;
        } else if(size.equals("M")){
            return COFFEE_M;
        } else {
            return COFFEE_L;
        }
    }
}