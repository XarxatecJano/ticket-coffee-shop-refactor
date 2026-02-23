package discounts;

import java.util.ArrayList;
import java.util.List;
import domain.Order;
import domain.OrderItem;

public class FreeMuffinDiscount implements IDiscount {
    private static final double MUFFIN_PRICE = 2.2;

    @Override
    public double applyDiscount(double subTotal, Order order){
        if(!order.getCoupon().equals("FREEMUFFIN")){
            return subTotal;
        }

        List<OrderItem> items = order.getItems();
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).isMuffin()){
                return subTotal - MUFFIN_PRICE;
            }
        }

        return subTotal;
    }
}