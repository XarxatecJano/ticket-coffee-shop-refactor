package domain;

import java.util.*;
import discounts.Discounts;
import pricing.Pricers;
import domain.OrderItem;
import discounts.IDiscount;
import pricing.IPricer;

public class Order {

    private static final double VAT_RATE = 0.10;
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

    public List<OrderItem> getItems(){
      return this.items;
    }

    public boolean isVip(){
      return this.vip;
    }

    public boolean isHappyHour(){
      return this.happyHour;
    }

    public String getCoupon(){
      return this.coupon;
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

        ProductType product = ProductType.valueOf(itemParts[0].toUpperCase());
        Size size = Size.valueOf(itemParts[1].toUpperCase());
        int quantity = Integer.parseInt(itemParts.length > 2 && itemParts[2].length() > 0 ? itemParts[2] : "1");
        String extras = itemParts.length > 3 ? itemParts[3] : "";

        IPricer pricer = Pricers.forProduct(product);
        return new OrderItem(product, pricer, size, quantity, extras);
    }

    public double calculateSubTotal(){
      double subTotal = 0;
      for(OrderItem item : items){
        subTotal += item.calculateBaseAndExtras(happyHour);
      }

      return subTotal;
    }

    double applyDiscounts(double subTotal){
      List<IDiscount> discounts = Discounts.forOrder(this);

      for(int i = 0; i < discounts.size(); i++){
        subTotal = discounts.get(i).applyDiscount(subTotal, this);
      }
      return subTotal;
    }

    double applyVatAndRound(double subTotal){
      subTotal += subTotal * VAT_RATE;
      return Math.round(subTotal * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    }

    public double calculateTotal(){
      double subTotal = calculateSubTotal();
      subTotal = applyDiscounts(subTotal);
      return applyVatAndRound(subTotal);
    }
}