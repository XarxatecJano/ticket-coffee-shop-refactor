package Discounts;

import java.util.function.Function;

public enum DiscountType {

    VIP;

    public Discount createDiscount(double discountPercentage) {

        switch (this) {
            case VIP:
                return new DiscountVIP(discountPercentage);
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public static DiscountType from(String name) {
        for (DiscountType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid discount type: " + name);
    }
}    