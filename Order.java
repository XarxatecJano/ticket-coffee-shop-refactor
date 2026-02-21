import java.util.*;

public class Order {

    private static final double VAT_RATE = 0.10;
    private static final double VIP_DISCOUNT = 0.50;
    private static final double VIP_THRESHOLD = 10.0;
    private static final double SAVE10_DISCOUNT = 0.10;
    private static final double MUFFIN_PRICE = 2.2;
    private static final double ROUNDING_FACTOR = 100.0;

    List<OrderItem> items;
    String coupon;
    boolean vip;
    boolean happyHour;

    Order(List<OrderItem> items, String coupon, boolean vip, boolean happyHour){
      this.items = items;
      this.coupon = coupon;
      this.vip = vip;
      this.happyHour = happyHour;
    }

    public static Order fromMap(Map<String, Object> map){
      List<String> items = (List<String>) map.get("items");

      List<OrderItem> parsedItem = new ArrayList<>();
      for(String item : items){
        parsedItem.add(parseItem(item));
      }

      String coupon = (String) map.get("coupon");
      boolean vip = Boolean.TRUE.equals(map.get("vip"));
      boolean happyHour = Boolean.TRUE.equals(map.get("happyHour"));

      return new Order(parsedItem, coupon, vip, happyHour);
    }

    private static OrderItem parseItem(String item){
        String[] itemParts = item.split("\\|");

        String product = itemParts[0];
        String size = itemParts[1];
        int quantity = Integer.parseInt(itemParts.length > 2 && itemParts[2].length() > 0 ? itemParts[2] : "1");
        String extras = itemParts.length > 3 ? itemParts[3] : "";

        return new OrderItem(product, size, quantity, extras);
    }

    double calculateSubTotal(){
      double subTotal = 0;
      for(OrderItem item : items){
        subTotal += item.calculateBaseAndExtras(happyHour);
      }

      return subTotal;
    }

    private static double applyCouponDiscount(double subTotal, Order order){
        String coupon = order.coupon;
        List<OrderItem> items = order.items;
        if(coupon != null && !coupon.equals("")){
        if(coupon.equals("SAVE10")){
            subTotal = subTotal - (subTotal * SAVE10_DISCOUNT);
        } else if(coupon.equals("FREEMUFFIN")) {
            for(OrderItem parseItem : items){
            if(parseItem.product.equals("muffin")){ 
                return subTotal - MUFFIN_PRICE;
            }
            }
        }
        }

        return subTotal;
    }
  
    private static double applyVipDiscount(double subTotal, boolean vip){
        if(vip && subTotal > VIP_THRESHOLD){
        return subTotal - VIP_DISCOUNT;
        }

        return subTotal;
    }

    double applyDiscounts(double subTotal){
      subTotal = applyCouponDiscount(subTotal, this);
      subTotal = applyVipDiscount(subTotal, vip);
      return subTotal;
    }

    double applyVatAndRound(double subTotal){
      subTotal += subTotal * VAT_RATE;
      return Math.round(subTotal * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    }

    double calculateTotal(){
      double subTotal = calculateSubTotal();
      subTotal = applyDiscounts(subTotal);
      return applyVatAndRound(subTotal);
    }

    String generateReceipt(){
      StringBuilder receipt = new StringBuilder();
      receipt.append("*** BYTE & BEAN ***\n");

      receipt.append("VIP:").append(vip ? "YES" : "NO")
        .append(" | HAPPY:").append(happyHour ? "YES" : "NO").append("\n");

      for(int i = 0; i < items.size(); i++){
        OrderItem parsedItem = items.get(i);

        receipt.append(parsedItem.product).append(" ")
        .append(parsedItem.size).append(" x")
        .append(parsedItem.quantity).append(" extras:")
        .append(parsedItem.extras).append("\n");
      }

      receipt.append("COUPON:").append(coupon == null ? "" : coupon).append("\n");
      receipt.append("TOTAL=").append(calculateTotal()).append(" EUR\n");

      return receipt.toString();
    }
}