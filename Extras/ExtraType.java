package Extras;

public enum ExtraType implements Extra {

    MILK,
    SHOT,
    SYRUP;

    @Override
    public double getPrice() {
        switch (this) {
            case MILK:
                return 0.2;
            case SHOT:
                return 0.8;
            case SYRUP:
                return 0.5;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public static ExtraType from(String name) {
        for (ExtraType type : ExtraType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid extra type: " + name);
    }
}