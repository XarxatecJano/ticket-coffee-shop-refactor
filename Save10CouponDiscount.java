public class Save10CouponDiscount implements IDiscount {
    private static final double SAVE10_DISCOUNT = 0.10;

    @Override
    public double applyDiscount(double subTotal, Order order){
        if(order.getCoupon().equals("SAVE10")){
            return subTotal - (subTotal * SAVE10_DISCOUNT);
        }

        return subTotal;
    }
}