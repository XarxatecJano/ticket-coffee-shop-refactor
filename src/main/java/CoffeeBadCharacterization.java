package src.main.java;

import java.util.*;

public class CoffeeBadCharacterization {

    public static void main(String[] args) {
        
        Map<String,Object> order1=new HashMap<>();
        order1.put ("items", Arrays.asList("coffee|M|2|milk,shot", "tea|S|1"));
        order1.put ("coupon", "SAVE10");
        order1.put ("vip", true);
        order1.put ("happyHour", true);

        double total1 = CoffeeBad.t(order1);
        assert total1 == 9.6 : "order1 total should be 9.60 but was " + total1;

        Map<String,Object> order2 = new HashMap<>();
        order2.put("items", Arrays.asList("muffin|L|2|", "coffe|S|1|syrup"));
        order2.put("coupon", "FREEMUFFIN");
        order2.put("vip", false);
        order2.put("happyHour", false);

        double total2 = CoffeeBad.t(order2);
        assert total2 == 5.17 : "order2 total should be 5.17 but was " + total2;
        
        String ticket = CoffeeBad.r(order1);
        assert ticket.contains("*** BYTE & BEAN ***") : "ticket header missing";
        assert ticket.contains("TOTAL=9.6 EUR") || ticket.contains("TOTAL=9.60 EUR") : "ticket total line missing/changed";
        
        System.out.println("  .-=-.\r\n" + //
                        " ,|`~'|\r\n" + //
                        " `|   |  Characterization test passed!!!\r\n" + //
                        "   `~'");
    }
}