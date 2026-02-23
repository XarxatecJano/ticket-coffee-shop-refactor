package src.main.java.bytebean.domain;

import java.util.List;

public record Order(
        List<OrderItem> items,
        Coupon coupon,
        boolean vip,
        boolean happyHour) {
}
