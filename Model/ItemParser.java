package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import Extras.ExtraType;  
import Products.ProductType;
import Products.Product;

public class ItemParser {

    public static CoffeeBadItem parse(String itemToParse) {

        String[] parts = itemToParse.split("\\|");

        String productName = parts[0];
        String size = parts[1];

        ProductType productType = ProductType.from(productName);
        Product product = productType.createProduct(size);

        int quantity = (parts.length > 2 && !parts[2].isEmpty())
                ? Integer.parseInt(parts[2])
                : 1;

        List<ExtraType> extras = parseExtras(parts.length > 3 ? parts[3] : "");

        return new CoffeeBadItem(product, quantity, extras);
    }

    private static List<ExtraType> parseExtras(String extrasString) {

        List<ExtraType> extras = new ArrayList<>();

        if (extrasString == null || extrasString.isEmpty()) {
            return extras;
        }

        String[] parts = extrasString.split(",");

        for (String part : parts) {
            extras.add(ExtraType.from(part.trim()));
        }

        return extras;
    }    
}