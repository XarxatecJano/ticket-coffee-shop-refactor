package discounts;

import java.util.ArrayList;
import java.util.List;
import domain.Order;
import domain.OrderItem;

public class FreeMuffinDiscount implements IDiscount {
    private static final double MUFFIN_PRICE = 2.2;

    @Override
    public double applyDiscount(double subTotal, Order order){
        if(!"FREEMUFFIN".equals(order.getCoupon())){
            return subTotal;
        }

        double cheapestMuffin = Double.MAX_VALUE;

        List<OrderItem> items = order.getItems();
        for(int i = 0; i < items.size(); i++){
            OrderItem item = items.get(i);
            if(item.isMuffin()){
                double price = item.calculateUnitBasePrice();
            
                if(price < cheapestMuffin){
                    cheapestMuffin = price;
                }
            }
        }

        if(cheapestMuffin == Double.MAX_VALUE){
            return subTotal;
        }

        return subTotal - cheapestMuffin;
    }
}