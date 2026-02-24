import java.util.*;

public class CoffeeBad{

    public enum Product {
        COFFEE_S("coffee","S", 2.0),
        COFFEE_M("coffee","M", 2.5),
        COFFEE_L("coffee", "L", 3.0),
        TEA_S   ("tea", "S", 1.5),
        TEA_M   ("tea", "M", 2.0),
        TEA_L   ("tea", "L", 2.3),
        MUFFIN  ("muffin", "",  2.2);

        private final String type;
        private final String size;
        private final double price;

        Product(String type, String size, double price) {
            this.type = type;
            this.size = size;
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public double getPrice() {
            return price;
        }

        public static Product find(String type, String size) {

            for (Product product : values()) {
                if (product.type.equals(type) && product.size.equals(size)) return product;
            }
            throw new IllegalArgumentException("Producto desconocido: " + type + " " + size);
        }
    }

    public enum Extra {
        MILK  ("milk", 0.20),
        SHOT  ("shot", 0.80),
        SYRUP ("syrup", 0.50);

        private final String name;

        private final double cost;

        Extra(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }

        public static double getCost(String name) {
            for (Extra extra : values()) {
                if (extra.name.equals(name)) return extra.cost;
            }
            return 0.0;
        }
    }

    public enum Coupon {
        SAVE10 ("SAVE10", 0.10),
        FREEMUFFIN ("FREEMUFFIN", 0.00);

        private final String code;

        private final double discountRate;

        Coupon(String code, double discountRate) {
            this.code = code;
            this.discountRate = discountRate;
        }

        public double getDiscountRate() {
            return discountRate;
        }

        public static Optional<Coupon> find(String code) {
            for (Coupon coupon : values()) {
                if (coupon.code.equals(code)) return Optional.of(coupon);
            }
            return Optional.empty();
        }
    }

    public static class OrderItem {

        private final Product product;
        private final int quantity;
        private final List<String> extras;

        private OrderItem(Product product, int quantity, List<String> extras) {
            this.product = product;
            this.quantity = quantity;
            this.extras = extras;
        }

        public static OrderItem fromString(String rawItemData) {

            String[] itemAttributes = rawItemData.split("\\|", -1);

            if (itemAttributes.length < 2) {
                throw new IllegalArgumentException("Formato de item inválido: " + rawItemData);
            }

            String type = itemAttributes[0];
            String size = itemAttributes[1];

            int quantity = itemAttributes.length > 2 && !itemAttributes[2].isEmpty()
                    ? Integer.parseInt(itemAttributes[2]) : 1;

            List<String> extras = itemAttributes.length > 3 && !itemAttributes[3].isEmpty()
                    ? Arrays.asList(itemAttributes[3].split(","))
                    : Collections.emptyList();

            return new OrderItem(Product.find(type, size), quantity, extras);
        }

        public Product getProduct() {
            return product;
        }
        public int getQuantity() { 
            return quantity; 
        }
        public List<String> getExtras() {
            return extras;   }
        }

    private static final double HAPPY_HOUR_DISCOUNT = 0.20;
    private static final double VIP_DISCOUNT_THRESHOLD = 10.0;
    private static final double VIP_DISCOUNT_AMOUNT = 0.50;
    private static final double TAX_RATE_IVA = 0.10;

    private static double calculateItemSubtotal(OrderItem item, boolean isHappyHour) {
        double basePrice = item.getProduct().getPrice();

        if (isHappyHour && item.getProduct().getType().equals("coffee")) {
            basePrice -= basePrice * HAPPY_HOUR_DISCOUNT;
        }

        double extrasTotal = item.getExtras().stream()
              .mapToDouble(Extra::getCost)
              .sum();

        return (basePrice + extrasTotal) * item.getQuantity();
    }

    private static double applyCoupon(double subtotal, String couponCode, List<OrderItem> items) {
        if (couponCode == null || couponCode.isEmpty()) return subtotal;

        Optional<Coupon> coupon = Coupon.find(couponCode);
        if (coupon.isEmpty()) return subtotal;

        return switch (coupon.get()) {
            case SAVE10 ->
                subtotal - (subtotal * Coupon.SAVE10.getDiscountRate());

            case FREEMUFFIN -> {
                boolean hasMuffin = items.stream()
                        .anyMatch(item -> item.getProduct().getType().equals("muffin"));
                yield hasMuffin ? subtotal - Product.MUFFIN.getPrice() : subtotal;
            }
        };
    }

    private static double applyVipDiscount(double subtotal, boolean isVip) {
        if (isVip && subtotal > VIP_DISCOUNT_THRESHOLD) {
            return subtotal - VIP_DISCOUNT_AMOUNT;
        }
        return subtotal;
    }

    private static double applyTax(double subtotal) {
        return subtotal + (subtotal * TAX_RATE_IVA);
    }

    public static double calculateTotal(Map<String, Object> order) {
        List<String> rawItems = (List<String>) order.get("items");
        boolean isHappyHour = Boolean.TRUE.equals(order.get("happyHour"));
        boolean isVip = Boolean.TRUE.equals(order.get("vip"));
        String couponCode = (String) order.get("coupon");

        List<OrderItem> items = rawItems.stream()
                .map(OrderItem::fromString)
                .toList();

        double subtotal = items.stream()
                .mapToDouble(item -> calculateItemSubtotal(item, isHappyHour))
                .sum();

        subtotal = applyCoupon(subtotal, couponCode, items);
        subtotal = applyVipDiscount(subtotal, isVip);

        double total = applyTax(subtotal);

        return Math.round(total * 100.0) / 100.0;
    }

    public static String renderReceipt(Map<String, Object> order) {
        StringBuilder ticketContent = new StringBuilder();
        ticketContent.append("*** BYTE & BEAN ***\n");
        ticketContent.append("VIP: ").append(Boolean.TRUE.equals(order.get("vip")) ? "YES" : "NO").append("\n");
        ticketContent.append("TOTAL: ").append(calculateTotal(order)).append(" EUR\n");
        return ticketContent.toString();
    }

    public static void main(String[] args) {
        Map<String, Object> order = new HashMap<>();
        order.put("items", Arrays.asList("coffee|M|2|milk,shot", "tea|S|1|", "muffin|S|1|"));
        order.put("coupon", "SAVE10");
        order.put("vip", true);
        order.put("happyHour", true);

        double total = calculateTotal(order);
        System.out.println(renderReceipt(order));
        System.out.println("Total calculado: " + total + " EUR");
    }
}