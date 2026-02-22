import java.util.*;

public class OrderItem {
    
    private static final double EXTRA_MILK = 0.2;
    private static final double EXTRA_SHOT = 0.8;
    private static final double EXTRA_SYRUP = 0.5;

    private final String productName;

    IPricer pricer;
    String size;
    int quantity;
    String extras;

    OrderItem(String productName, IPricer pricer, String size, int quantity, String extras){
      this.productName = productName;
      this.pricer = pricer;
      this.size = size;
      this.quantity = quantity;
      this.extras = extras;
    }

    String getProductName() {
        return productName;
    }

    double calculateBaseAndExtras(boolean happyHour){
      double base = pricer.calculateBasePrice(size, happyHour);
      double extrasPrice = calculateExtrasPrice(extras);
      return (base + extrasPrice) * quantity;
    }

    boolean isMuffin() {
        return pricer instanceof MuffinPricer;
    }

    private static double calculateExtrasPrice(String extras){
        double extrasPrice = 0;
        if(extras != null && extras.length() > 0){
            String[] extraParts = extras.split(",");
            for(int k = 0; k < extraParts.length; k++){
            if(extraParts[k].equals("milk")){
                extrasPrice += EXTRA_MILK;
            } else if(extraParts[k].equals("shot")){
                extrasPrice += EXTRA_SHOT;
            } else if(extraParts[k].equals("syrup")){
                extrasPrice += EXTRA_SYRUP;
            }
            }
        }

        return extrasPrice;
    }
}