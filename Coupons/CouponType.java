package Coupons;

public enum CouponType {
    SAVE10,
    FREEMUFFIN;

    public Coupon createCoupon() {
        switch (this) {
            case SAVE10:
                return new CouponSave10();
            case FREEMUFFIN:
                return new CouponFreeMuffin();
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public static CouponType from(String name) {
        for (CouponType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid coupon type: " + name);
    }
}