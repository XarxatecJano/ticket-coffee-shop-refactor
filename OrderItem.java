import java.util.*;

public class OrderItem {

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

    public String getSize() {
        return this.size;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getExtras() {
        return this.extras;
    }

    boolean isCoffee(){
        return pricer instanceof CoffeePricer;
    }

    double calculateBaseAndExtras(boolean happyHour){
      double base = pricer.calculateBasePrice(size);

        if(happyHour && isCoffee()){
            base -= base * 0.20;
        }

      double extrasPrice = calculateExtrasPrice(extras);
      return (base + extrasPrice) * quantity;
    }

    boolean isMuffin() {
        return pricer instanceof MuffinPricer;
    }

    private static double calculateExtrasPrice(String extras){
        if(extras == null || extras.length() == 0){
            return 0;
        }

        double extrasPrice = 0;
        String[] extraParts = extras.split(",");

            for(int k = 0; k < extraParts.length; k++){
                String extra = extraParts[k];
                IExtraPricer pricer = ExtraPrice.createExtra(extra);
                extrasPrice += pricer.getPrice();
            }

        return extrasPrice;
    }
}