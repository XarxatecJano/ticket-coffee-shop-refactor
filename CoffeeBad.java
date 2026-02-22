
import java.util.*;

public class CoffeeBad {

    public enum ItemType{
        COFFEE, TEA, MUFFIN
    }

    public enum Size{
        S, M, L
    }

    public enum Extra{
        MILK, SHOT, SYRUP
    }

    public static class Menu {
        public static final Map<String, Map<String, Double>> BASE_PRICES = Map.of(
            "coffee", Map.of("S", 2.0, "M", 2.5, "L", 3.0), 
            "tea", Map.of("S", 1.5, "M", 2.0, "L", 2.3), 
            "muffin", Map.of("ANY", 2.2) 
            );
        public static final Map<String, Double> EXTRA_PRICES = Map.of(
            "milk", 0.2, 
            "shot", 0.8, 
            "syrup", 0.5 
            );
     }      

    public static class OrderLine { 
        public final ItemType type; 
        public final Size size; 
        public final int quantity; 
        public final List<Extra> extras;
         
        public OrderLine(ItemType type, Size size, int quantity, List<Extra> extras) { 
            this.type = type; 
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
            String type = line.type.name().toLowerCase(); 
            String size = line.size.name(); 
            Map<String, Double> sizeMap = Menu.BASE_PRICES.get(type); 
            double base = sizeMap.getOrDefault(size, sizeMap.get("ANY"));
            
            if (happyHour && line.type == ItemType.COFFEE) { 
                base *= 0.8;
            }
            double extras = 0; 
            for (Extra e : line.extras) { 
                extras += Menu.EXTRA_PRICES.get(e.name().toLowerCase()); 
                } 
                return (base + extras) * line.quantity; 
                } 
                
                public double subtotal(List<OrderLine> lines, boolean happyHour) { 
                    double sum = 0; 
                    for (OrderLine l : lines) { 
                        sum += priceLine(l, happyHour); 
                        } 
                        return sum; 
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
            private static final double MUFFIN_PRICE = 2.2;
            @Override 
            public double apply(double total, List<OrderLine> lines) { 
                boolean hasMuffin = false; 
                for (OrderLine l : lines) { 
                    if (l.type == ItemType.MUFFIN) { 
                        hasMuffin = true; break;
                        } 
                    } 
                    return hasMuffin ? total - MUFFIN_PRICE : total; 
                }
            } 
        public static class DiscountRegistry { 
            public static final Map<String, Discount> COUPONS = Map.of( 
                "SAVE10", new Save10Discount(), 
                "FREEMUFFIN", new FreeMuffinDiscount() 
            ); 
        }

    public static class TaxService { 
        private static final double TAX_RATE = 0.10; 
        public double applyTax(double total) { 
            return total * (1 + TAX_RATE); 
        } 
    } 
    public static class VipService { 
        private static final double VIP_THRESHOLD = 10.0; 
        private static final double VIP_DISCOUNT = 0.5; 
        public double applyVip(double total, boolean vip) {
             if (vip && total > VIP_THRESHOLD) { 
                return total - VIP_DISCOUNT; 
            } 
            return total; 
        } 
    }
    public static class OrderCalculator { 
        private final PricingService pricing = new PricingService(); 
        private final TaxService tax = new TaxService(); 
        private final VipService vip = new VipService(); 
        public double calculate(Order order) { 
            double total = pricing.subtotal(order.lines, order.happyHour);

            if (order.coupon != null && !order.coupon.isEmpty()) { 
                Discount discount = DiscountRegistry.COUPONS.get(order.coupon);
                if (discount != null) {
                    total = discount.apply(total, order.lines);
                    } 
                } 
                
                total = vip.applyVip(total, order.vip); 
                total = tax.applyTax(total); 
                return Math.round(total * 100.0) / 100.0; } }

    public static class ReceiptService { 
        public String generate(Order order, double total) { 
            StringBuilder sb = new StringBuilder("*** BYTE & BEAN ***\n"); 
            sb.append("VIP:").append(order.vip ? "YES" : "NO") 
            .append(" | HAPPY:").append(order.happyHour ? "YES" : "NO") 
            .append("\n"); for (OrderLine l : order.lines) { 
                sb.append(l.type).append(" ") 
                .append(l.size).append(" x") 
                .append(l.quantity).append(" extras:") 
                .append(l.extras.isEmpty() ? "" : l.extras) 
                .append("\n"); 
            } 
            sb.append("COUPON:").append(order.coupon == null ? "" : order.coupon)
            .append("\nTOTAL=").append(total).append(" EUR\n"); 
            return sb.toString(); 
            } 
        } 
        private static ItemType parseItemType(String raw) { 
            return switch (raw.toLowerCase()) { 
                case "coffee" -> ItemType.COFFEE; 
                case "tea" -> ItemType.TEA; 
                case "muffin" -> ItemType.MUFFIN; 
                default -> throw new IllegalArgumentException("Unknown item: " + raw); 
                }; 
            } 
            private static Size parseSize(String raw) { 
                if (raw == null || raw.isEmpty()) 
                return Size.ANY; 
                return Size.valueOf(raw); 
            } 
            private static Extra parseExtra(String raw) { 
                return switch (raw.toLowerCase()) { 
                    case "milk" -> Extra.MILK; 
                    case "shot" -> Extra.SHOT; 
                    case "syrup" -> Extra.SYRUP; 
                    default -> throw new IllegalArgumentException("Unknown extra: " + raw); 
                    }; 
                } 
            private static List<OrderLine> parseItems(List<String> itemStrings) { 
                return itemStrings.stream() .flatMap(str -> { 
                    String[] parts = str.split("\\|"); 
                    String name = parts[0]; 
                    String sizeStr = parts.length > 1 ? parts[1] : ""; 
                    String qtyStr = parts.length > 2 && !parts[2].isEmpty() ? parts[2] : "1"; 
                    String extrasStr = parts.length > 3 ? parts[3] : ""; 
                    
                    ItemType type = parseItemType(name); 
                    Size size = parseSize(sizeStr); 
                    int quantity = Integer.parseInt(qtyStr); 
                    
                    List<Extra> extras = extrasStr.isEmpty() 
                    ? List.of() 
                    : Arrays.stream(extrasStr.split(",")) 
                    .filter(s -> !s.isBlank()) 
                    .map(String::trim) 
                    .map(CoffeeBad::parseExtra) 
                    .toList(); 
                    
                return Stream.of(new OrderLine(type, size, quantity, extras));
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
                            @SuppressWarnings("unchecked") 
                            List<String> itemStrings = (List<String>) raw.get("items"); 
                            List<OrderLine> lines = parseItems(itemStrings); 
                            
                            boolean vip = (boolean) raw.get("vip"); 
                            boolean happyHour = (boolean) raw.get("happyHour"); 
                            String coupon = (String) raw.get("coupon"); 
                            double expected = (double) raw.get("expectedTotal"); 
                            
                            Order order = new Order(lines, vip, happyHour, coupon); 
                            double total = calculator.calculate(order); 
                            if (Math.abs(total - expected) > 0.001) { 
                                throw new AssertionError("Expected " + expected + " but got " + total); 
                            } 
                            System.out.println(receiptService.generate(order, total)); 
                            }); 
                    System.out.println("All assertions passed ✅"); 
                    } 
                }