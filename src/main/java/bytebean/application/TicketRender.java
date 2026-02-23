package src.main.java.bytebean.application;

import src.main.java.bytebean.domain.Coupon;
import src.main.java.bytebean.domain.Extra;
import src.main.java.bytebean.domain.Order;
import src.main.java.bytebean.domain.OrderItem;
import src.main.java.bytebean.domain.Product;
import src.main.java.bytebean.domain.Size;

public class TicketRender {

    public String render(Order order, double total) {

        StringBuilder x = new StringBuilder();
        x.append("*** BYTE & BEAN ***\n");
        x.append("VIP:").append(order.vip() ? "YES" : "NO")
                .append(" | HAPPY:").append(order.happyHour() ? "YES" : "NO")
                .append("\n");

        for (OrderItem item : order.items()) {
            x.append(toRawName(item.product()))
                    .append("")
                    .append(toRawSize(item.size()))
                    .append(" x")
                    .append(item.quantity())
                    .append(renderExtras(item))
                    .append("\n");
        }

        x.append("COUPON:").append(order.coupon() == Coupon.NONE ? "" : order.coupon().name()).append("\n");
        x.append("TOTAL=").append(total).append(" EUR\n");
        return x.toString();
    }

    private Object toRawSize(Size s) {
        return switch (s) {
            case S -> "S";
            case M -> "M";
            case L -> "L";
            case UNKNOW -> throw new UnsupportedOperationException("Unimplemented case: " + s);
            default -> "";
        };

    }

    private Object renderExtras(OrderItem item) {
        if (item.extras().isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < item.extras().size(); i++) {
            if (i > 0)
                sb.append(",");
            sb.append(toRawExtra(item.extras().get(i)));
        }
        return sb.toString();
    }

    private String toRawName(Product p) {
        return switch (p) {
            case COFFEE -> "coffe";
            case TEA -> "tea";
            case MUFFIN -> "muffin";
            default -> "";
        };
    }

    private String toRawExtra(Extra e) {
        return switch (e) {
            case MILK -> "milk";
            case SHOT -> "shot";
            case SYRUP -> "syrup";
            default -> "";
        };
    }
}
