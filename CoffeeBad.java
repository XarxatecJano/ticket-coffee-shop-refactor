import java.util.*;

public class CoffeeBad {

    public enum Size {
        S, M, L, ANY
    }

    public enum Extra {
        MILK, SHOT, SYRUP
    }

    public interface Product {
        double basePrice(Size size, boolean happyHour);
    }

    public static class Coffee implements Product {
        private static final Map<Size, Double> PRICES = Map.of(
                Size.S, 2.0,
                Size.M, 2.5,
                Size.L, 3.0
        );

        @Override
        public double basePrice(Size size, boolean happyHour) {
            double base = PRICES.get(size);
            return happyHour ? base * 0.8 : base;
        }
    }

    public static class Tea implements Product {
        private static final Map<Size, Double> PRICES = Map.of(
                Size.S, 1.5,
                Size.M, 2.0,
                Size.L, 2.3
        );

        @Override
        public double basePrice(Size size, boolean happyHour) {
            return PRICES.get(size);
        }
    }

    public static class Muffin implements Product {
        @Override
        public double basePrice(Size size, boolean happyHour) {
            return 2.2;
        }
    }

    public static class ProductRegistry {
        private static final Map<String, Product> PRODUCTS = Map.of(
                "coffee", new Coffee(),
                "tea", new Tea(),
                "muffin", new Muffin()
        );

        public static Product get(String name) {
            return PRODUCTS.get(name.toLowerCase());
        }
    }

    public static class ExtraRegistry {
        private static final Map<String, Extra> EXTRAS = Map.of(
                "milk", Extra.MILK,
                "shot", Extra.SHOT,
                "syrup", Extra.SYRUP
        );

        private static final Map<Extra, Double> PRICES = Map.of(
                Extra.MILK, 0.2,
                Extra.SHOT, 0.8,
                Extra.SYRUP, 0.5
        );

        public static Extra get(String name) {
            return EXTRAS.get(name.toLowerCase());
        }

        public static double price(Extra e) {
            return PRICES.get(e);
        }
    }

    public static class OrderLine {
        public final Product product;
        public final Size size;
        public final int quantity;
        public final List<Extra> extras;

        public OrderLine(Product product, Size size, int quantity, List<Extra> extras) {
            this.product = product;
            this.size = size;
            this.quantity = quantity;
            this.extras = extras;
        }
    }

    public static class Order {
        public final List<OrderLine> lines;
        public final boolean vip;
        public final boolean happyHour;
        public final String coupon;

        public Order(List<OrderLine> lines, boolean vip, boolean happyHour, String coupon) {
            this.lines = lines;
            this.vip = vip;
            this.happyHour = happyHour;
            this.coupon = coupon;
        }
    }

    public static class PricingService {
        public double priceLine(OrderLine line, boolean happyHour) {
            double base = line.product.basePrice(line.size, happyHour);
            double extras = line.extras.stream()
                    .mapToDouble(ExtraRegistry::price)
                    .sum();
            return (base + extras) * line.quantity;
        }

        public double subtotal(List<OrderLine> lines, boolean happyHour) {
            return lines.stream()
                    .mapToDouble(l -> priceLine(l, happyHour))
                    .sum();
        }
    }

    public interface Discount {
        double apply(double total, List<OrderLine> lines);
    }

    public static class Save10Discount implements Discount {
        @Override
        public double apply(double total, List<OrderLine> lines) {
            return total * 0.9;
        }
    }

    public static class FreeMuffinDiscount implements Discount {
        @Override
        public double apply(double total, List<OrderLine> lines) {
            boolean hasMuffin = lines.stream().anyMatch(l -> l.product instanceof Muffin);
            return hasMuffin ? total - 2.2 : total;
        }
    }

    public static class DiscountRegistry {
        private static final Map<String, Discount> COUPONS = Map.of(
                "SAVE10", new Save10Discount(),
                "FREEMUFFIN", new FreeMuffinDiscount()
        );

        public static Discount get(String code) {
            return COUPONS.get(code);
        }
    }

    public static class TaxService {
        public double applyTax(double total) {
            return total * 1.10;
        }
    }

    public static class VipService {
        public double applyVip(double total, boolean vip) {
            return vip && total > 10 ? total - 0.5 : total;
        }
    }

    public static class OrderCalculator {
        private final PricingService pricing = new PricingService();
        private final TaxService tax = new TaxService();
        private final VipService vip = new VipService();

        public double calculate(Order order) {
            double total = pricing.subtotal(order.lines, order.happyHour);
            if (order.coupon != null && !order.coupon.isEmpty()) {
                Discount d = DiscountRegistry.get(order.coupon);
                if (d != null) total = d.apply(total, order.lines);
            }
            total = vip.applyVip(total, order.vip);
            total = tax.applyTax(total);
            return Math.round(total * 100.0) / 100.0;
        }
    }

    public static class ReceiptService {
        public String generate(Order order, double total) {
            StringBuilder sb = new StringBuilder("*** BYTE & BEAN ***\n");
            sb.append("VIP:").append(order.vip ? "YES" : "NO")
              .append(" | HAPPY:").append(order.happyHour ? "YES" : "NO")
              .append("\n");
            for (OrderLine l : order.lines) {
                sb.append(l.product.getClass().getSimpleName().toUpperCase())
                  .append(" ").append(l.size)
                  .append(" x").append(l.quantity)
                  .append(" extras:").append(l.extras)
                  .append("\n");
            }
            sb.append("COUPON:").append(order.coupon == null ? "" : order.coupon)
              .append("\nTOTAL=").append(total).append(" EUR\n");
            return sb.toString();
        }
    }

    private static Size parseSize(String raw) {
        if (raw == null || raw.isEmpty()) return Size.ANY;
        return Size.valueOf(raw);
    }

    private static List<OrderLine> parseItems(List<String> itemStrings) {
        return itemStrings.stream()
                .map(str -> {
                    String[] p = str.split("\\|");
                    Product product = ProductRegistry.get(p[0]);
                    Size size = parseSize(p[1]);
                    int qty = Integer.parseInt(p.length > 2 && !p[2].isEmpty() ? p[2] : "1");
                    List<Extra> extras = p.length > 3 && !p[3].isEmpty()
                            ? Arrays.stream(p[3].split(","))
                                    .map(String::trim)
                                    .map(ExtraRegistry::get)
                                    .toList()
                            : List.of();
                    return new OrderLine(product, size, qty, extras);
                })
                .toList();
    }

    public static void main(String[] args) {
    OrderCalculator calculator = new OrderCalculator();
    ReceiptService receiptService = new ReceiptService();

    List<Map<String, Object>> rawOrders = List.of(
            Map.of(
                    "items", List.of("coffee|M|2|milk,shot", "tea|S|1|", "muffin|S|1|"),
                    "coupon", "SAVE10",
                    "vip", true,
                    "happyHour", true,
                    "expectedTotal", 9.60
            ),
            Map.of(
                    "items", List.of("muffin|L|2|", "coffee|S|1|syrup"),
                    "coupon", "FREEMUFFIN",
                    "vip", false,
                    "happyHour", false,
                    "expectedTotal", 5.17
            )
    );

    rawOrders.forEach(raw -> {
        List<?> rawList = (List<?>) raw.get("items");
        List<String> itemStrings = rawList.stream()
            .map(Object::toString)
            .toList();

        List<OrderLine> lines = parseItems(itemStrings);

        boolean vip = (boolean) raw.get("vip");
        boolean happyHour = (boolean) raw.get("happyHour");
        String coupon = (String) raw.get("coupon");
        double expected = (double) raw.get("expectedTotal");

        Order order = new Order(lines, vip, happyHour, coupon);
        double total = calculator.calculate(order);

        if (Math.abs(total - expected) > 0.001)
            throw new AssertionError("Expected " + expected + " but got " + total);

        System.out.println(receiptService.generate(order, total));
    });

    System.out.println("All assertions passed ✅");
    }
}