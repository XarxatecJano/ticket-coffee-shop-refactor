
import java.util.*;

public class CoffeeBad {

  public static double calculateOrderTotal(Map<String,Object> order){
    double subTotal = 0;

    List<String> items = (List<String>) order.get("items");

    for(int i=0; i<items.size(); i++){
      String item = items.get(i);
      String[] itemParts = item.split("\\|");

      String product = itemParts[0];
      String size = itemParts[1];
      int quantity = Integer.parseInt(itemParts.length > 2 && itemParts[2].length() > 0 ? itemParts[2] : "1");
      String extras = itemParts.length > 3 ? itemParts[3] : "";

      double basePrice = 0;

      if(product.equals("coffee")){
        if(size.equals("S")){
          basePrice = 2.0;
        } else if(size.equals("M")) {
            basePrice = 2.5;
        } else {
          basePrice = 3.0;
        }

        if(Boolean.TRUE.equals(order.get("happyHour"))){ 
          basePrice = basePrice - (basePrice * 0.2); 
        }

      }else if(product.equals("tea")){
        if(size.equals("S")){
          basePrice = 1.5;
        } else if(size.equals("M")){
          basePrice = 2.0;
        }else{
          basePrice = 2.3;
        }

      }else if(product.equals("muffin")){
        basePrice = 2.2;

      }else{
        basePrice = 0;
      }

      double extrasPrice = 0;

      if(extras != null && extras.length() > 0){
        String[] extraParts=extras.split(",");

        for(int k=0;k<extraParts.length;k++){
          if(extraParts[k].equals("milk")){
            extrasPrice += 0.2;
          } else if(extraParts[k].equals("shot")){
            extrasPrice += 0.8;
          } else if(extraParts[k].equals("syrup")){
            extrasPrice += 0.5;
          } else{
            extrasPrice += 0;
          }
        }
      }

      subTotal = subTotal + ( (basePrice * quantity) + (extrasPrice * quantity) );
    }

    String coupon = (String) order.get("coupon");
    if(coupon != null && !coupon.equals("")){
      if(coupon.equals("SAVE10")){
        subTotal = subTotal - (subTotal * 0.10);
      } else if(coupon.equals("FREEMUFFIN")) {
        boolean found = false;
        for(String it : items){
          if(it.startsWith("muffin|")){ 
            found=true; 
            break; 
          }
        }

        if(found){ 
          subTotal = subTotal - 2.2; 
        }

      }
    }

    Boolean vip = (Boolean) order.get("vip");
    if(Boolean.TRUE.equals(vip)){
      if(subTotal > 10){ 
        subTotal = subTotal - 0.5; 
      }
    }

    subTotal = subTotal + (subTotal * 0.10);
    subTotal = Math.round(subTotal * 100.0) / 100.0;
    return subTotal;
  }

  public static String generateReceipt(Map<String,Object> order){
    StringBuilder receipt = new StringBuilder();
    receipt.append("*** BYTE & BEAN ***\n");

    receipt.append("VIP:").append(Boolean.TRUE.equals(order.get("vip")) ? "YES" : "NO")
      .append(" | HAPPY:").append(Boolean.TRUE.equals(order.get("happyHour")) ? "YES" : "NO").append("\n");

    List<String> items = (List<String>) order.get("items");

    for(int i = 0; i < items.size(); i++){
      String[] parts = items.get(i).split("\\|");

      receipt.append(parts[0]).append(" ").append(parts[1]).append(" x").append(parts[2]).append(" extras:")
       .append(parts.length> 3 ? parts[3] : "").append("\n");
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