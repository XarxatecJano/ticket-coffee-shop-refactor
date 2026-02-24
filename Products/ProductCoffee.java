package Products;

public class ProductCoffee implements Product {
    private String size;

    private static final double COFFEE_SMALL = 2.0;
    private static final double COFFEE_MEDIUM = 2.5;
    private static final double COFFEE_LARGE = 3.0;

    private static final double HAPPY_HOUR = 0.20;


    public ProductCoffee(String size) {
        this.size = size;
    }

    @Override
    public double getBasePrice(boolean happyHour) {

        double price;

        if (size.equals("S")) price = COFFEE_SMALL;
        else if (size.equals("M")) price = COFFEE_MEDIUM;
        else price = COFFEE_LARGE;

        if (happyHour) {
            price -= price * HAPPY_HOUR;
        }
        return price;
    }
}    