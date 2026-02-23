package discounts;

import domain.Order;

public class VipDiscount implements IDiscount {
    private static final double VIP_DISCOUNT = 0.50;
    private static final double VIP_THRESHOLD = 10.0;

    @Override
    public double applyDiscount(double subTotal, Order order){
        if(order.isVip() && subTotal > VIP_THRESHOLD){
            return subTotal - VIP_DISCOUNT;
        }

        return subTotal;
    }
}