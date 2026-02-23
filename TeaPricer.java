public class TeaPricer implements IPricer {
    private static final double TEA_S = 1.5;
    private static final double TEA_M = 2.0;
    private static final double TEA_L = 2.3;

    @Override
    public double calculateBasePrice(String size) {
        if(size.equals("S")){
            return TEA_S;
        } else if(size.equals("M")){
            return TEA_M;
        } else {
            return TEA_L;
        }
    }
}