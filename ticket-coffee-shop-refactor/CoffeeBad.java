import java.util.*;

public class CoffeeBad {

  private static final double VAR_RATE = 0.10; 
  private static final double HAPPY_HOUR_DISCOUNT = 0.20; 
  private static final double SAVE_10_DISCOUNT = 0.10; 
  private static final double FREE_MUFFIN_DISCOUNT = 2.20; 
  private static final double VIP_DISCOUNT = 0.50; 
  private static final double VIP_MIN_TOTAL = 10.0; 

  private static final Map<String, Double> BASE_PRICES = new HashMap<>();
  static {
      BASE_PRICES.put("coffee|S", 2.0);
      BASE_PRICES.put("coffee|M", 2.5);
      BASE_PRICES.put("coffee|L", 3.0);
      BASE_PRICES.put("tea|S", 1.5);
      BASE_PRICES.put("tea|M", 2.0);
      BASE_PRICES.put("tea|L", 2.3);
      BASE_PRICES.put("muffin|S", 2.2);
      BASE_PRICES.put("muffin|M", 2.2);
      BASE_PRICES.put("muffin|L", 2.2);
  }

  private static final Map<String, Double> EXTRAS_PRICES = new HashMap<>();
  static {
      EXTRAS_PRICES.put("milk", 0.20);
      EXTRAS_PRICES.put("shot", 0.80);
      EXTRAS_PRICES.put("syrup", 0.50);
  }

  public static double calculateTotal(Map<String, Object> order) {
      List<String> items = (List<String>) order.get("items");
      boolean isHappyHour = Boolean.TRUE.equals(order.get("happyHour"));
      boolean isVip = Boolean.TRUE.equals(order.get("vip"));
      String coupon = (String) order.get("coupon");

      double subtotal = calculateItemsSubtotal(items, isHappyHour);
      subtotal = applyCoupon(subtotal, coupon, items);
      subtotal = applyVipDiscount(subtotal, isVip);
      subtotal = applyVat(subtotal);

      return Math.round(subtotal * 100.0) / 100.0;
  }

  private static double calculateItemsSubtotal(List<String> items, boolean isHappyHour) {
      double subtotal = 0;
      for (String itemRaw : items) {
          subtotal += calculateItemPrice(itemRaw, isHappyHour);
      }
      return subtotal;
  }

  private static double calculateItemPrice(String itemRaw, boolean isHappyHour) {
      String[] parts = itemRaw.split("\\|");
      String productName = parts[0];
      String size = parts[1];
      int quantity = determineQuantity(parts);
      String itemExtras = parts.length > 3 ? parts[3] : "";

      double basePrice = getBasePrice(productName, size);
      basePrice = applyHappyHour(basePrice, productName, isHappyHour);
      double extrasPrice = calculateExtrasPrice(itemExtras);

      return (basePrice + extrasPrice) * quantity;
  }

  private static double getBasePrice(String productName, String size) {
      String key = productName + "|" + size;
      return BASE_PRICES.getOrDefault(key, 0.0);
  }

  private static double applyHappyHour(double price, String productName, boolean isHappyHour) {
      return (isHappyHour && "coffee".equals(productName))
          ? price * (1 - HAPPY_HOUR_DISCOUNT)
          : price;
  }

  private static double calculateExtrasPrice(String extrasRaw) {
      double total = 0;
      for (String extra : extrasRaw.split(",")) {
          total += EXTRAS_PRICES.getOrDefault(extra, 0.0);
      }
      return total;
  }

  private static int determineQuantity(String[] parts) {
      return (parts.length > 2 && !parts[2].isEmpty())
          ? Integer.parseInt(parts[2])
          : 1;
  }

  private static double applyCoupon(double subtotal, String coupon, List<String> items) {
      Map<String, Double> discounts = new HashMap<>();
      discounts.put("SAVE10",     subtotal * (1 - SAVE_10_DISCOUNT));
      discounts.put("FREEMUFFIN", applyFreeMuffin(subtotal, items));
      return discounts.getOrDefault(coupon, subtotal);
  }

  private static double applyFreeMuffin(double subtotal, List<String> items) {
      boolean hasMuffin = items.stream().anyMatch(item -> item.startsWith("muffin|"));
      return hasMuffin ? subtotal - FREE_MUFFIN_DISCOUNT : subtotal;
  }

  private static double applyVipDiscount(double subtotal, boolean isVip) {
      return (isVip && subtotal > VIP_MIN_TOTAL)
          ? subtotal - VIP_DISCOUNT
          : subtotal;
  }

  private static double applyVat(double subtotal) {
      return subtotal * (1 + VAT_RATE);
  }

  public static String generateTicket(Map<String, Object> order) {
      boolean isVip       = Boolean.TRUE.equals(order.get("vip"));
      boolean isHappyHour = Boolean.TRUE.equals(order.get("happyHour"));
      List<String> items  = (List<String>) order.get("items");
      String coupon   = order.get("coupon") == null ? "" : (String) order.get("coupon");

      StringBuilder ticket = new StringBuilder();
      ticket.append("*** BYTE & BEAN ***\n");
      ticket.append("VIP: ").append(isVip ? "YES" : "NO");
      ticket.append(" | HAPPY HOUR: ").append(isHappyHour ? "YES" : "NO").append("\n");

      for (String itemRaw : items) {
          String[] parts = itemRaw.split("\\|");
          ticket.append(parts[0]).append(" ").append(parts[1])
                .append(" x").append(parts.length > 2 ? parts[2] : "1")
                .append(" extras:").append(parts.length > 3 ? parts[3] : "")
                .append("\n");
      }

      ticket.append("COUPON: ").append(coupon).append("\n");
      ticket.append("TOTAL: ").append(calculateTotal(order)).append(" EUR\n");
      return ticket.toString();
  }

  public static void main(String[] args) {
      Map<String, Object> order1 = new HashMap<>();
      order1.put("items", Arrays.asList("coffee|M|2|milk,shot", "tea|S|1|", "muffin|S|1|"));
      order1.put("coupon", "SAVE10");
      order1.put("vip", true);
      order1.put("happyHour", true);

      double total1 = calculateTotal(order1);
      assert total1 == 9.60 : "order1 debería ser 9.60 pero fue " + total1;

      Map<String, Object> order2 = new HashMap<>();
      order2.put("items", Arrays.asList("muffin|L|2|", "coffee|S|1|syrup"));
      order2.put("coupon", "FREEMUFFIN");
      order2.put("vip", false);
      order2.put("happyHour", false);

      double total2 = calculateTotal(order2);
      assert total2 == 5.17 : "order2 debería ser 5.17 pero fue " + total2;

      System.out.println(generateTicket(order1));
      System.out.println("Todos los asserts pasaron ✅");
  }
}  