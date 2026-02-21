import java.util.*;

public class OrderItem {

    private static final double HAPPY_HOUR_DISCOUNT = 0.20;
  
    private static final double COFFEE_S = 2.0;
    private static final double COFFEE_M = 2.5;
    private static final double COFFEE_L = 3.0;

    private static final double TEA_S = 1.5;
    private static final double TEA_M = 2.0;
    private static final double TEA_L = 2.3;

    private static final double EXTRA_MILK = 0.2;
    private static final double EXTRA_SHOT = 0.8;
    private static final double EXTRA_SYRUP = 0.5;
    
    private static final double MUFFIN_PRICE = 2.2;

    String product;
    String size;
    int quantity;
    String extras;

    OrderItem(String product, String size, int quantity, String extras){
      this.product = product;
      this.size = size;
      this.quantity = quantity;
      this.extras = extras;
    }

    double calculateBaseAndExtras(boolean happyHour){
      double base = calculateBasePrice(product, size, happyHour);
      double extrasPrice = calculateExtrasPrice(extras);
      return (base + extrasPrice) * quantity;
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

    private static double calculateBasePrice(String product, String size, boolean happyHour){
        double basePrice = 0;

        if(product.equals("coffee")) {
            if(size.equals("S")){
            basePrice = COFFEE_S;
            } else if(size.equals("M")) {
                basePrice = COFFEE_M;
            } else {
            basePrice = COFFEE_L;
            }

            if(happyHour){ 
            basePrice = basePrice - (basePrice * HAPPY_HOUR_DISCOUNT); 
            }

        } else if(product.equals("tea")) {
            if(size.equals("S")){
            basePrice = TEA_S;
            } else if(size.equals("M")){
            basePrice = TEA_M;
            }else{
            basePrice = TEA_L;
            }

        } else if(product.equals("muffin")) {
            basePrice = MUFFIN_PRICE;
        }

        return basePrice;
    }
}