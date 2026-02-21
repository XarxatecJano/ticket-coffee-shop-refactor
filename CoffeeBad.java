
import java.util.*;

public class CoffeeBad {

  private static final double VAT_RATE = 0.10;
  private static final double HAPPY_HOUR_DISCOUNT = 0.20;
  private static final double SAVE10_DISCOUNT = 0.10;
  private static final double VIP_DISCOUNT = 0.50;
  private static final double VIP_THRESHOLD = 10.0;

  private static final double COFFEE_S = 2.0;
  private static final double COFFEE_M = 2.5;
  private static final double COFFEE_L = 3.0;

  private static final double TEA_S = 1.5;
  private static final double TEA_M = 2.0;
  private static final double TEA_L = 2.3;

  private static final double MUFFIN_PRICE = 2.2;

  private static final double EXTRA_MILK = 0.2;
  private static final double EXTRA_SHOT = 0.8;
  private static final double EXTRA_SYRUP = 0.5;

  private static final double ROUNDING_FACTOR = 100.0;

  private static class ParsedItem {
    String product;
    String size;
    int quantity;
    String extras;

    ParsedItem(String product, String size, int quantity, String extras){
      this.product = product;
      this.size = size;
      this.quantity = quantity;
      this.extras = extras;
    }
  }

  private static ParsedItem parseItem(String item){
    String[] itemParts = item.split("\\|");

    String product = itemParts[0];
    String size = itemParts[1];
    int quantity = Integer.parseInt(itemParts.length > 2 && itemParts[2].length() > 0 ? itemParts[2] : "1");
    String extras = itemParts.length > 3 ? itemParts[3] : "";

    return new ParsedItem(product, size, quantity, extras);
  }

  private static double applyCouponDiscount(double subTotal, String coupon, List<String> items){
    if(coupon != null && !coupon.equals("")){
      if(coupon.equals("SAVE10")){
        subTotal = subTotal - (subTotal * SAVE10_DISCOUNT);
      } else if(coupon.equals("FREEMUFFIN")) {
        for(String it : items){
          if(it.startsWith("muffin|")){ 
            return subTotal - MUFFIN_PRICE;
          }
        }
      }
    }

    return subTotal;
  }
  
  private static double applyVipDiscount(double subTotal, boolean vip){
    if(vip && subTotal > VIP_THRESHOLD){
      return subTotal - VIP_THRESHOLD;
    }

    return subTotal;
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

  public static double calculateOrderTotal(Map<String,Object> order){
    double subTotal = 0;

    List<String> items = (List<String>) order.get("items");

    for(int i=0; i<items.size(); i++){
      String item = items.get(i);
      
      ParsedItem parsedItem = parseItem(item); 

      boolean happyHour = Boolean.TRUE.equals(order.get("happyHour"));
      double basePrice = calculateBasePrice(parsedItem.product, parsedItem.size, happyHour);
      double extrasPrice = calculateExtrasPrice(parsedItem.extras);

      subTotal = subTotal + ( (basePrice * parsedItem.quantity) + (extrasPrice * parsedItem.quantity) );
    }

    String coupon = (String) order.get("coupon");
    Boolean vip = (Boolean) order.get("vip");
    
    subTotal = applyCouponDiscount(subTotal, coupon, items);
    subTotal = applyVipDiscount(subTotal, Boolean.TRUE.equals(vip));

    subTotal = subTotal + (subTotal * VAT_RATE);
    subTotal = Math.round(subTotal * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    return subTotal;
  }

  public static String generateReceipt(Map<String,Object> order){
    StringBuilder receipt = new StringBuilder();
    receipt.append("*** BYTE & BEAN ***\n");

    receipt.append("VIP:").append(Boolean.TRUE.equals(order.get("vip")) ? "YES" : "NO")
      .append(" | HAPPY:").append(Boolean.TRUE.equals(order.get("happyHour")) ? "YES" : "NO").append("\n");

    List<String> items = (List<String>) order.get("items");

    for(int i = 0; i < items.size(); i++){
      String item = items.get(i);
      ParsedItem parsedItem = parseItem(item);

      receipt.append(parsedItem.product).append(" ")
      .append(parsedItem.size).append(" x")
      .append(parsedItem.quantity).append(" extras:")
      .append(parsedItem.extras).append("\n");
    }

    receipt.append("COUPON:").append(order.get("coupon") == null ? "" : (String) order.get("coupon")).append("\n");
    receipt.append("TOTAL=").append(calculateOrderTotal(order)).append(" EUR\n");

    return receipt.toString();
  }

  public static void main(String[] args){
    Map<String,Object> order1=new HashMap<>();
    order1.put("items", Arrays.asList("coffee|M|2|milk,shot", "tea|S|1|", "muffin|S|1|"));
    order1.put("coupon","SAVE10");
    order1.put("vip",true);
    order1.put("happyHour",true);

    double total1 = calculateOrderTotal(order1);
    assert total1 == 9.60 : "order1 total should be 9.60 but was " + total1;

    Map<String,Object> order2 = new HashMap<>();
    order2.put("items", Arrays.asList("muffin|L|2|", "coffee|S|1|syrup"));
    order2.put("coupon","FREEMUFFIN");
    order2.put("vip",false);
    order2.put("happyHour",false);

    double total2 = calculateOrderTotal(order2);
    assert total2 == 5.17 : "order2 total should be 5.17 but was " + total2;

    System.out.println(generateReceipt(order1));
    System.out.println("All assertions passed ✅");
  }
}