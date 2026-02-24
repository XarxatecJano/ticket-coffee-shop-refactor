package Products;

public enum ProductType {

    COFFEE,
    TEA,
    MUFFIN;

    public Product createProduct(String size) {

        switch (this) {
            case COFFEE:
                return new ProductCoffee(size);
            case TEA:
                return new ProductTea(size);
            case MUFFIN:
                return new ProductMuffin(size);
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public static ProductType from(String name) {
        for (ProductType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid product type: " + name);
    }
}