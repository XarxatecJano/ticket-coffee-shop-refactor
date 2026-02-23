import java.util.*;
import service.CoffeeService;
import domain.Order;
import presentation.ReceiptFormatter;

public class Main {
    public static void main(String[] args){
    Map<String,Object> order1=new HashMap<>();
    order1.put("items", Arrays.asList("coffee|M|2|milk,shot", "tea|S|1|", "muffin|S|1|"));
    order1.put("coupon","SAVE10");
    order1.put("vip",true);
    order1.put("happyHour",true);

    double total1 = CoffeeService.calculateOrderTotal(order1);
    assert total1 == 9.60 : "order1 total should be 9.60 but was " + total1;

    Map<String,Object> order2 = new HashMap<>();
    order2.put("items", Arrays.asList("muffin|L|2|", "coffee|S|1|syrup"));
    order2.put("coupon","FREEMUFFIN");
    order2.put("vip",false);
    order2.put("happyHour",false);

    double total2 = CoffeeService.calculateOrderTotal(order2);
    assert total2 == 5.17 : "order2 total should be 5.17 but was " + total2;

    Map<String,Object> order3 = new HashMap<>();
    order3.put("items", Arrays.asList("muffin|M|3", "coffee|L|4|milk,shot,syrup", "tea|S|2"));
    order3.put("coupon", "SAVE10");
    order3.put("vip", false);
    order3.put("happyHour", false);

    double total3 = CoffeeService.calculateOrderTotal(order3);
    assert total3 == 27.32 : "order3 total should be 27.32 but was " + total3;

    Map<String,Object> order4 = new HashMap<>();
    order4.put("items", Arrays.asList("coffee|L|4|")); 
    order4.put("coupon","");
    order4.put("vip",true);
    order4.put("happyHour",false);

    double total4 = CoffeeService.calculateOrderTotal(order4);
    assert total4 == 12.65 : "order3 total should be 12.65 but was " + total4;

    Map<String,Object> order5 = new HashMap<>();
    order5.put("items", Arrays.asList("tea|M|2|milk"));
    order5.put("coupon","");
    order5.put("vip",false);
    order5.put("happyHour",true);

    double total5 = CoffeeService.calculateOrderTotal(order5);
    assert total5 == 4.84 : "order5 total should be 4.84 but was " + total5;

    Map<String,Object> order6 = new HashMap<>();
    order6.put("items", Arrays.asList("coffee|M|2|", "tea|M|1|"));
    order6.put("coupon","");
    order6.put("vip",false);
    order6.put("happyHour",true);

    double total6 = CoffeeService.calculateOrderTotal(order6);
    assert total6 == 6.60 : "order6 total should be 6.60 but was " + total6;

    Map<String,Object> order7 = new HashMap<>();
    order7.put("items", Arrays.asList("muffin|S|3|"));
    order7.put("coupon","FREEMUFFIN");
    order7.put("vip",false);
    order7.put("happyHour",false);

    double total7 = CoffeeService.calculateOrderTotal(order7);
    assert total7 == 4.84 : "order7 total should be 4.84 but was " + total7;

    Map<String,Object> order8 = new HashMap<>();
    order8.put("items", Arrays.asList("coffee|M|1|"));
    order8.put("coupon","FREEMUFFIN");
    order8.put("vip",false);
    order8.put("happyHour",false);

    double total8 = CoffeeService.calculateOrderTotal(order8);
    assert total8 == 2.75 : "order7 total should be 2.75 but was " + total8;

    Map<String,Object> order9 = new HashMap<>();
    order9.put("items", new ArrayList<>());
    order9.put("coupon","");
    order9.put("vip",false);
    order9.put("happyHour",false);

    double total9 = CoffeeService.calculateOrderTotal(order9);
    assert total9 == 0.0 : "order7 total should be 0.0 but was " + total9;

    System.out.println(CoffeeService.generateReceipt(order1));
    System.out.println(CoffeeService.generateReceipt(order2));
    System.out.println(CoffeeService.generateReceipt(order3));
    System.out.println(CoffeeService.generateReceipt(order4));
    System.out.println(CoffeeService.generateReceipt(order5));
    System.out.println(CoffeeService.generateReceipt(order6));
    System.out.println(CoffeeService.generateReceipt(order7));
    System.out.println(CoffeeService.generateReceipt(order8));
    System.out.println(CoffeeService.generateReceipt(order9));

    System.out.println("All assertions passed ✅");
  }
}