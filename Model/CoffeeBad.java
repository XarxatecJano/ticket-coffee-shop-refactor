package Model;

import java.util.*;

import Discounts.*;
import Products.*;
import Extras.*;
import Coupons.*;

public class CoffeeBad {

  private static final double VAT = 0.10;

  private static double applyCoupon(Map<String, Object> order, double subtotal, List<String> items) {
    String coupon = (String) order.get("coupon");

    if (coupon == null || coupon.isEmpty()) {
        return subtotal;
    }

    Coupon couponType = CouponType.from(coupon).createCoupon();
    subtotal = couponType.applyCoupon(subtotal, items);
    return subtotal;
  }

  private static double applyDiscount(double subtotal, Boolean vip) {
    if (vip){
      DiscountType discountType = DiscountType.from("VIP");
      subtotal = discountType.createDiscount(0.2).applyDiscount(subtotal);
    }
    return subtotal;
  }

  private static double applyVAT(double subtotal) {
    return subtotal + (subtotal * VAT);
  }

  private static double roundPrice(double value) {
    return Math.round(value * 100.0) / 100.0;
  }

  private static double calculateTotalItemPrice(String item, Boolean happyHour) {
    CoffeeBadItem itemParsed = ItemParser.parse(item);
    return itemParsed.getTotalPrice(happyHour);
  }

  public static double ticket(Map<String,Object> order){
    double subTotal = 0;
    List<String> items = (List<String>) order.get("items");
    Boolean vip = (Boolean) order.get("vip");
    boolean happyHour = Boolean.TRUE.equals(order.get("happyHour"));

    for (String product : items) {
      subTotal += calculateTotalItemPrice(product, happyHour);
    }

    subTotal = applyCoupon(order, subTotal, items);

    subTotal = applyDiscount(subTotal, vip);

    subTotal = applyVAT(subTotal);

    subTotal = roundPrice(subTotal);
    
    return subTotal;
  }

  public static String toString (Map<String,Object> order) {
    StringBuilder result = new StringBuilder();
    result.append("*** BYTE & BEAN ***\n");
    result.append("--------------------\n");
    result.append("VIP:").append(Boolean.TRUE.equals(order.get("vip"))?"YES":"NO")
     .append(" | HAPPY:").append(Boolean.TRUE.equals(order.get("happyHour"))?"YES":"NO").append("\n");

    List<String> items = (List<String>)order.get("items");
    for ( int i=0 ; i<items.size() ; i++) {
      String[] item = items.get(i).split("\\|");
      result.append(item[0]).append(" ").append(item[1]).append(" x").append(item[2]).append(" extras:")
       .append(item.length>3 ? item[3] : "").append(" = ").append(calculateTotalItemPrice(items.get(i), Boolean.TRUE.equals(order.get("happyHour")))).append("\n");
    }
    result.append("COUPON:").append(order.get("coupon")==null?"":(String)order.get("coupon")).append("\n");
    result.append("TOTAL=").append(ticket(order)).append(" EUR\n");
    return result.toString();
  }
}