package discounts;

import domain.Order;

public interface IDiscount {
    double applyDiscount(double subTotal, Order order);
}