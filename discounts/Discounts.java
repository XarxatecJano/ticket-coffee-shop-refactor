package discounts;

import java.util.ArrayList;
import java.util.List;
import domain.Order;

public class Discounts {
    public static List<IDiscount> forOrder(Order order){
        List<IDiscount> discounts = new ArrayList<>();

        String coupon = order.getCoupon();
        if(coupon != null && !coupon.equals("")){
            switch(coupon){
                case "SAVE10":
                    discounts.add(new Save10CouponDiscount());
                    break;
                
                case "FREEMUFFIN":
                    discounts.add(new FreeMuffinDiscount());
                    break;
                
                default:
                    break;
            }
        }

        discounts.add(new VipDiscount());

        return discounts;
    }
}