package Coupons;

import java.util.List;
public interface Coupon {
    double applyCoupon(double subtotal, List<String> items);
}