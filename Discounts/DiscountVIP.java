package Discounts;

public class DiscountVIP implements Discount {
    private double discountPercentage;

    private static final double VIP_MINIMUM_AMOUNT = 10.0;

    public DiscountVIP(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double applyDiscount(double subtotal) {
        if (subtotal >= VIP_MINIMUM_AMOUNT) {
            return subtotal * (1 - discountPercentage / 100.0);
        }
        return subtotal;
    }
}    