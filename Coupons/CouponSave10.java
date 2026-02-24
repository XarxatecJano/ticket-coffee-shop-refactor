package Coupons;

import java.util.List;
import java.util.Map;

public class CouponSave10 implements Coupon {
    private static final double SAVE10 = 0.10;

    @Override
    public double applyCoupon(double subtotal, List<String> items) {
        return subtotal - (subtotal * SAVE10);
    }
}