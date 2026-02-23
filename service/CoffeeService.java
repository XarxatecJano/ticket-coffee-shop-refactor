package service;

import java.util.*;
import domain.Order;
import presentation.ReceiptFormatter;

public class CoffeeService {

  public static double calculateOrderTotal(Map<String,Object> orderMap){
    Order order = Order.fromMap(orderMap);
    return order.calculateTotal();
  }

  public static String generateReceipt(Map<String,Object> orderMap){
    Order order = Order.fromMap(orderMap);
    ReceiptFormatter formatter = new ReceiptFormatter();
    return formatter.format(order);
  }
}