package Model;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import Extras.ExtraType;
import Products.ProductType;
import Products.Product;

public class CoffeeBadItem {

  private Product product;
  private int quantity;
  private List<ExtraType> extras;
  
  public CoffeeBadItem(Product product, int quantity, List<ExtraType> extras) {
    this.product = product;
    this.quantity = quantity;
    this.extras = extras;
  }

  public double getTotalPrice(Boolean happyHour) {
    double basePrice = this.product.getBasePrice(happyHour);
    double extrasPrice = calculateExtrasPrice(this.extras);
    return (basePrice + extrasPrice) * this.quantity;
  }

  public static Double calculateExtrasPrice(List<ExtraType> extras){
    double total = 0.0;

    for (ExtraType extra : extras) {
        total += extra.getPrice();
    }

    return total;
  }
}