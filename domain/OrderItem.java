package domain;

import java.util.*;
import pricing.IPricer;
import extras.IExtraPricer;
import extras.ExtraPrice;
import pricing.CoffeePricer;
import pricing.MuffinPricer;

public class OrderItem {

    private static final double HAPPY_HOUR_DISCOUNT = 0.20;
    private final ProductType productType;

    private final IPricer pricer;
    private final Size size;
    private final int quantity;
    private final String extras;

    OrderItem(ProductType productType, IPricer pricer, Size size, int quantity, String extras){
      this.productType = productType;
      this.pricer = pricer;
      this.size = size;
      this.quantity = quantity;
      this.extras = extras;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public Size getSize() {
        return this.size;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getExtras() {
        return this.extras;
    }

    public boolean isCoffee(){
        return pricer instanceof CoffeePricer;
    }

    public boolean isMuffin() {
        return pricer instanceof MuffinPricer;
    }

    public double calculateUnitBasePrice(){
        return pricer.calculateBasePrice(this.size);
    }

    double calculateBaseAndExtras(boolean happyHour){
      double base = pricer.calculateBasePrice(size);

        if(happyHour && isCoffee()){
            base -= base * HAPPY_HOUR_DISCOUNT;
        }

      double extrasPrice = calculateExtrasPrice(extras);
      return (base + extrasPrice) * quantity;
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