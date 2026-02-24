package Products;

public class ProductMuffin implements Product {
    private String size;

    private static final double MUFFIN_SMALL = 2.2;
    private static final double MUFFIN_MEDIUM = 2.2;
    private static final double MUFFIN_LARGE = 2.2;

    public ProductMuffin(String size) {
        this.size = size;
    }

    @Override
    public double getBasePrice(boolean happyHour) {

        double price;

        if (size.equals("S")) price = MUFFIN_SMALL;
        else if (size.equals("M")) price = MUFFIN_MEDIUM;
        else price = MUFFIN_LARGE;

        return price;
    }
}    