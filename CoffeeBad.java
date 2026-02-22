
import java.util.*;

public class CoffeeBad {

    public enum ItemType{
        COFFEE, TEA, MUFFIN
    }

    public enum Size{
        S, M, L
    }

    public enum Extra{
        MILK, SHOT, SYRUP
    }

    public static class Menu {
        public static final Map<String, Map<String, Double>> BASE_PRICES = Map.of(
            "coffee", Map.of("S", 2.0, "M", 2.5, "L", 3.0), 
            "tea", Map.of("S", 1.5, "M", 2.0, "L", 2.3), 
            "muffin", Map.of("ANY", 2.2) 
            );
        public static final Map<String, Double> EXTRA_PRICES = Map.of(
            "milk", 0.2, 
            "shot", 0.8, 
            "syrup", 0.5 
            );
     }      

    public static class OrderLine { 
        public final ItemType type; 
        public final Size size; 
        public final int quantity; 
        public final List<Extra> extras;
         
        public OrderLine(ItemType type, Size size, int quantity, List<Extra> extras) { 
            this.type = type; 
            this.size = size; 
            this.quantity = quantity; 
            this.extras = extras; 
            } 
        } 
        
        public static class Order { 
            public final List<OrderLine> lines; 
            public final boolean vip; 
            public final boolean happyHour; 
            public final String coupon; 
            
            public Order(List<OrderLine> lines, boolean vip, boolean happyHour, String coupon) { 
                this.lines = lines; 
                this.vip = vip; 
                this.happyHour = happyHour; 
                this.coupon = coupon; 
                } 
            }
    public static class PricingService { 
        public double priceLine(OrderLine line, boolean happyHour) {
            String type = line.type.name().toLowerCase(); 
            String size = line.size.name(); 
            Map<String, Double> sizeMap = Menu.BASE_PRICES.get(type); 
            double base = sizeMap.getOrDefault(size, sizeMap.get("ANY"));
            
            if (happyHour && line.type == ItemType.COFFEE) { 
                base *= 0.8;
            }
            double extras = 0; 
            for (Extra e : line.extras) { 
                extras += Menu.EXTRA_PRICES.get(e.name().toLowerCase()); 
                } 
                return (base + extras) * line.quantity; 
                } 
                
                public double subtotal(List<OrderLine> lines, boolean happyHour) { 
                    double sum = 0; 
                    for (OrderLine l : lines) { 
                        sum += priceLine(l, happyHour); 
                        } 
                        return sum; 
                        } 
                }

    public interface Discount { 
        double apply(double total, List<OrderLine> lines); 
        } 
        public static class Save10Discount implements Discount { 
            @Override 
            public double apply(double total, List<OrderLine> lines) { 
                return total * 0.9; 
            } 
        } 
        
        public static class FreeMuffinDiscount implements Discount { 
            private static final double MUFFIN_PRICE = 2.2;
            @Override 
            public double apply(double total, List<OrderLine> lines) { 
                boolean hasMuffin = false; 
                for (OrderLine l : lines) { 
                    if (l.type == ItemType.MUFFIN) { 
                        hasMuffin = true; break;
                        } 
                    } 
                    return hasMuffin ? total - MUFFIN_PRICE : total; 
                }
            } 
        public static class DiscountRegistry { 
            public static final Map<String, Discount> COUPONS = Map.of( 
                "SAVE10", new Save10Discount(), 
                "FREEMUFFIN", new FreeMuffinDiscount() 
            ); 
        }

    public static double t(Map<String,Object> o){
        double s=0;
        List<String> items=(List<String>)o.get("items");
        for(int i=0;i<items.size();i++){
        String p=items.get(i);
        String[] a=p.split("\\|");
        String it=a[0];
        String z=a[1];
        int q=Integer.parseInt(a.length>2 && a[2].length()>0 ? a[2] : "1");
        String ex=a.length>3 ? a[3] : "";
        double b=0;

      if(it.equals("coffee")){
        if(z.equals("S")){b=2.0;}else if(z.equals("M")){b=2.5;}else{b=3.0;}
        if(Boolean.TRUE.equals(o.get("happyHour"))){ b=b-(b*0.2); }
      }else if(it.equals("tea")){
        if(z.equals("S")){b=1.5;}else if(z.equals("M")){b=2.0;}else{b=2.3;}
      }else if(it.equals("muffin")){
        b=2.2;
      }else{
        b=0;
      }

      double e=0;
      if(ex!=null && ex.length()>0){
        String[] parts=ex.split(",");
        for(int k=0;k<parts.length;k++){
          if(parts[k].equals("milk")){e+=0.2;}
          else if(parts[k].equals("shot")){e+=0.8;}
          else if(parts[k].equals("syrup")){e+=0.5;}
          else{e+=0;}
        }
      }

      s = s + ( (b*q) + (e*q) );
    }

    String coupon=(String)o.get("coupon");
    if(coupon!=null && !coupon.equals("")){
      if(coupon.equals("SAVE10")){
        s = s - (s*0.10);
      }else if(coupon.equals("FREEMUFFIN")){
        boolean found=false;
        for(String it : items){
          if(it.startsWith("muffin|")){ found=true; break; }
        }
        if(found){ s = s - 2.2; }
      }
    }

    Boolean vip=(Boolean)o.get("vip");
    if(Boolean.TRUE.equals(vip)){
      if(s>10){ s = s - 0.5; }
    }

    s = s + (s*0.10);
    s = Math.round(s*100.0)/100.0;
    return s;
  }

  public static String r(Map<String,Object> o){
    StringBuilder x=new StringBuilder();
    x.append("*** BYTE & BEAN ***\n");
    x.append("VIP:").append(Boolean.TRUE.equals(o.get("vip"))?"YES":"NO")
     .append(" | HAPPY:").append(Boolean.TRUE.equals(o.get("happyHour"))?"YES":"NO").append("\n");
    List<String> items=(List<String>)o.get("items");
    for(int i=0;i<items.size();i++){
      String[] a=items.get(i).split("\\|");
      x.append(a[0]).append(" ").append(a[1]).append(" x").append(a[2]).append(" extras:")
       .append(a.length>3 ? a[3] : "").append("\n");
    }
    x.append("COUPON:").append(o.get("coupon")==null?"":(String)o.get("coupon")).append("\n");
    x.append("TOTAL=").append(t(o)).append(" EUR\n");
    return x.toString();
  }

  public static void main(String[] args){
    Map<String,Object> order1=new HashMap<>();
    order1.put("items", Arrays.asList("coffee|M|2|milk,shot", "tea|S|1|", "muffin|S|1|"));
    order1.put("coupon","SAVE10");
    order1.put("vip",true);
    order1.put("happyHour",true);

    double total1=t(order1);
    assert total1==9.60 : "order1 total should be 9.60 but was "+total1;

    Map<String,Object> order2=new HashMap<>();
    order2.put("items", Arrays.asList("muffin|L|2|", "coffee|S|1|syrup"));
    order2.put("coupon","FREEMUFFIN");
    order2.put("vip",false);
    order2.put("happyHour",false);

    double total2=t(order2);
    assert total2==5.17 : "order2 total should be 5.17 but was "+total2;

    System.out.println(r(order1));
    System.out.println("All assertions passed ✅");
  }
}