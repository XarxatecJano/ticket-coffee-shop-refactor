package App;

import java.util.*;

import Model.*;

public class CoffeeBadApp {
    public static void main(String[] args){
    
    System.out.println("\n");
    System.out.println("BYTE & BEAN TICKET PRINTING TESTS");  
    System.out.println("\n");

    Map<String,Object> order1=new HashMap<>();
    order1.put("items", Arrays.asList("coffee|M|2|milk,shot", "tea|S|1|", "muffin|S|1|"));
    order1.put("coupon","SAVE10");
    order1.put("vip",true);
    order1.put("happyHour",true);

    double total1=CoffeeBad.ticket(order1);
    assert total1==9.60 : "order1 total should be 9.60 but was "+total1;

    Map<String,Object> order2=new HashMap<>();
    order2.put("items", Arrays.asList("muffin|L|2|", "coffee|S|1|syrup"));
    order2.put("coupon","FREEMUFFIN");
    order2.put("vip",false);
    order2.put("happyHour",false);

    double total2=CoffeeBad.ticket(order2);
    assert total2==5.17 : "order2 total should be 5.17 but was "+total2;

    System.out.println("TEST 1");
    System.out.println(CoffeeBad.toString(order1));
    System.out.println("TEST 2");
    System.out.println(CoffeeBad.toString(order2));
    System.out.println("All assertions passed ✅");
  }
}