package src.main.java.bytebean;

import java.util.Map;

import src.main.java.bytebean.application.OrderCalculator;
import src.main.java.bytebean.application.TicketRender;
import src.main.java.bytebean.domain.Order;
import src.main.java.bytebean.infrastructure.OrderParser;

import java.util.HashMap;
import java.util.Arrays;



public class CoffeeBad {

    public static double t(Map<String, Object> o) {
        OrderParser parser = new OrderParser();
        Order order = parser.parse(o);

        OrderCalculator calculator = new OrderCalculator();
        return calculator.calculateTotal(order);
    }

    public static String r(Map<String, Object> o) {
        OrderParser parser = new OrderParser();
        Order order = parser.parse(o);

        OrderCalculator calculator = new OrderCalculator();
        double total = calculator.calculateTotal(order);

        TicketRender renderer = new TicketRender();
        return renderer.render(order, total);
    }

    public static void main(String[] args) {

        Map<String, Object> order1 = new HashMap<>();
        order1.put("items", Arrays.asList(
                "coffee|M|2|milk,shot",
                "tea|S|1|",
                "muffin|S|1|"
        ));
        order1.put("coupon", "SAVE10");
        order1.put("vip", true);
        order1.put("happyHour", true);

        double total1 = t(order1);
        assert total1 == 9.60 : "order1 total should be 9.60 but was " + total1;

        Map<String, Object> order2 = new HashMap<>();
        order2.put("items", Arrays.asList(
                "muffin|L|2",
                "coffee|S|1|syrup"
        ));
        order2.put("coupon", "FREEMUFFIN");
        order2.put("vip", false);
        order2.put("happyHour", false);

        double total2 = t(order2);
        assert total2 == 5.17 : "order2 total should be 5.17 but was " + total2;

        System.out.println(r(order1));
        System.out.println("All assertions passed");
    }
}