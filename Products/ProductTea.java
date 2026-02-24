package Products;

public class ProductTea implements Product {
    private String size;

    private static final double TEA_SMALL = 1.5;
    private static final double TEA_MEDIUM = 2.0;
    private static final double TEA_LARGE = 2.3;

    private static final double HAPPY_HOUR = 0.20;


    public ProductTea(String size) {
        this.size = size;
    }

    @Override
    public double getBasePrice(boolean happyHour) {

        double price;

        if (size.equals("S")) price = TEA_SMALL;
        else if (size.equals("M")) price = TEA_MEDIUM;
        else price = TEA_LARGE;

        return price;
    }
}    