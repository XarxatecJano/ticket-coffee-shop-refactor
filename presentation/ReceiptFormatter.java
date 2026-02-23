package presentation;

import java.util.*;
import service.CoffeeService;
import domain.Order;
import domain.OrderItem;

public class ReceiptFormatter {
    public String format(Order order){
        StringBuilder receipt = new StringBuilder();
        receipt.append("*** BYTE & BEAN ***\n");

        receipt.append("VIP:").append(order.isVip() ? "YES" : "NO")
            .append(" | HAPPY:").append(order.isHappyHour() ? "YES" : "NO").append("\n");

        List<OrderItem> items = order.getItems();
        for(int i = 0; i < items.size(); i++){
            OrderItem parsedItem = items.get(i);

            receipt.append(parsedItem.getProductType()).append(" ")
            .append(parsedItem.getSize()).append(" x")
            .append(parsedItem.getQuantity()).append(" extras:")
            .append(parsedItem.getExtras()).append("\n");
        }

        receipt.append("COUPON:").append(order.getCoupon() == null ? "" : order.getCoupon()).append("\n");
        receipt.append("TOTAL=").append(order.calculateTotal()).append(" EUR\n");

        return receipt.toString();
    }
}