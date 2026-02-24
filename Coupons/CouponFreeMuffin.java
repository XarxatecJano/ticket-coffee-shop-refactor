package Coupons;

import java.util.List;

import Products.ProductMuffin;

public class CouponFreeMuffin implements Coupon {

    @Override
    public double applyCoupon(double subtotal, List<String> items) {
        double cheapestMuffinPrice = Double.MAX_VALUE;

        for (String item : items) {
            if (item.startsWith("muffin|")) {

                String[] itemParts = item.split("\\|");
                double muffinPrice = new ProductMuffin(itemParts[1]).getBasePrice(false); 

                if (muffinPrice < cheapestMuffinPrice) {
                        cheapestMuffinPrice = muffinPrice;
                }
            }
        }

        if (cheapestMuffinPrice != Double.MAX_VALUE) {
                return subtotal - cheapestMuffinPrice;
        }
        return subtotal;
    }
}