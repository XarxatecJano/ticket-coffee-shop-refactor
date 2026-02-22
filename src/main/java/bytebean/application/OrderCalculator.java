package src.main.java.bytebean.application;

import java.util.List;

import src.main.java.bytebean.domain.Coupon;
import src.main.java.bytebean.domain.Extra;
import src.main.java.bytebean.domain.Order;
import src.main.java.bytebean.domain.OrderItem;
import src.main.java.bytebean.domain.Product;
import src.main.java.bytebean.domain.Size;

public class OrderCalculator {

    public double calculateTotal(Order order){
        double subtotal = calculateSubtotal(order.items(), order.happyHour());
        subtotal = applyCoupon(subtotal, order.coupon(), order.items(), order.happyHour());
        subtotal = applyVipDiscount(subtotal, order.vip());
        double total = applyVat(subtotal);
        return roundEu(total);
    }

    private double calculateSubtotal(List<OrderItem> items, boolean happyHour) {
        double sum = 0.0;
        for (OrderItem item : items) {
            double base = unitBasePrice(item, happyHour);
            double extras = unitExtrasPrice(item);
            
            sum += (base + extras) * item.quantity();
        }
        return sum;
    }

    private double unitBasePrice(OrderItem item, boolean happyHour) {
        double base;
        
        if (item.product() == Product.COFFEE) {
            
            if (item.size() == Size.S)
                base = 2.0;
            else if (item.size() == Size.M)
                base =2.5;
            else
                base= 3.0;

            if (happyHour) base = base - (base * 0.20);

        } else if (item.product() == Product.TEA){

            if (item.size() == Size.S)
                base = 1.5;
            else if (item.size() == Size.M)
                base = 2.0;
            else
                base = 2.3;

        } else if (item.product() == Product.MUFFIN){
            
            base = 2.2;

        } else {

            base = 0.0;

        }

        return base;
        
    }

    private double unitExtrasPrice(OrderItem item) {
        double e = 0.0;

        for (Extra ex : item.extras()) {
            if (ex == Extra.MILK)
                e += 0.2;
            else if (ex == Extra.SHOT)
                e += 0.8;
            else if (ex == Extra.SYRUP)
                e += 0.5;
            else 
                e += 0.0;     
        }

        return e;
    }

    private double applyCoupon(double subtotal, Coupon coupon, List <OrderItem> items, boolean happyHour){
        
        if (coupon == Coupon.SAVE10){
            return subtotal - (subtotal * 0.10);
        }
        
        if (coupon == Coupon.FREEMUFFIN) {
            Double cheapestMuffinUnit = findCheapestMuffinUnitPrice(items, happyHour);
            if (cheapestMuffinUnit != null) {
                return subtotal - cheapestMuffinUnit;
            }
            return subtotal;
        }
        
        return subtotal;
        
    }

    private Double findCheapestMuffinUnitPrice(List<OrderItem> items, boolean happyHour) {
        Double min = null;

        for (OrderItem item : items) {
            if (item.product() != Product.MUFFIN) continue;
            if (item.quantity() <= 0) continue;

            double unit = unitBasePrice(item, happyHour) + unitExtrasPrice(item);
            if (min == null || unit < min)
                min = unit;
        }
        
        return min;
    }

    private double applyVipDiscount(double subtotal, boolean vip) {

        if (vip && subtotal > 10.0)
            return subtotal - 0.5;

        return subtotal;

    }

    private double applyVat(double subtotal){
        return subtotal + (subtotal * 0.10);
    }

    private double roundEu(double v) {
        return Math.round(v*100.0) /100.0;
    }

} 