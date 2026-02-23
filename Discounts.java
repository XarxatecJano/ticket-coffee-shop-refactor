import java.util.ArrayList;
import java.util.List;

public class Discounts {
    public static List<IDiscount> forOrder(Order order){
        List<IDiscount> discounts = new ArrayList<>();

        String coupon = order.getCoupon();
        if(coupon == null || coupon.length() == 0){
            return discounts;
        }

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

        discounts.add(new VipDiscount());

        return discounts;
    }
}