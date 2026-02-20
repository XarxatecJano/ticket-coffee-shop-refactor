package src.main.java.bytebean.infrastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import src.main.java.bytebean.domain.Coupon;
import src.main.java.bytebean.domain.Extra;
import src.main.java.bytebean.domain.Order;
import src.main.java.bytebean.domain.OrderItem;
import src.main.java.bytebean.domain.Product;
import src.main.java.bytebean.domain.Size;

public class OrderParser {

    @SuppressWarnings("unchecked")
    public Order parse(Map<String, Object> raw) {
        
        List<String> rawItems = (List<String>) raw.getOrDefault("items", Collections.emptyList());
        List<OrderItem> items = new ArrayList<>();
        for (String line : rawItems) {
            if (line == null || line.trim().isEmpty()) continue;
            items.add(parseItem(line));
        }

        String couponRaw = (String) raw.get("coupon");
        Coupon coupon = parseCoupon(couponRaw);

        boolean vip = Boolean.TRUE.equals(raw.get("vip"));
        boolean happy = Boolean.TRUE.equals(raw.get("happyHour"));

        return new Order(items, coupon, vip, happy);
    }

    public OrderItem parseItem(String line) {
        String[] parts = line.split("\\|", -1);

        String productRaw = part(parts, 0);
        String sizeRaw    = part(parts, 1);
        String qtyRaw     = part(parts, 2);
        String extrasRaw  = parts.length > 3 ? parts[3] : "";

        Product product = parseProduct(productRaw);
        Size size       = parseSize(sizeRaw);
        int qty         = parseQuantity(qtyRaw);
        List<Extra> extras = parseExtras(extrasRaw);

        return new OrderItem(product, size, qty, extras);
    }

    private String part(String[] parts, int idx) {
        if (parts == null || idx < 0 || idx >= parts.length) return "";
        String v = parts[idx];
        return v == null ? "" : v.trim();
    }

    private int parseQuantity(String s) {
        if (s == null || s.trim().isEmpty()) return 1;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException ex) {
            return 1;
        }
    }

    private List<Extra> parseExtras(String raw) {
        if (raw == null || raw.trim().isEmpty()) return List.of();
        String[] tokens = raw.split(",");
        List<Extra> extras = new ArrayList<>();
        for (String t : tokens) {
            String x = t == null ? "" : t.trim();
            if (x.isEmpty()) continue;
            extras.add(parseExtra(x));
        }
        return extras;
    }

    private Product parseProduct(String s) {
        switch (safeLower(s)) {
            case "coffee": return Product.COFFEE;
            case "tea":    return Product.TEA;
            case "muffin": return Product.MUFFIN;
            default:       return Product.UNKNOWN;
        }
    }

    private Size parseSize(String s) {
        switch (safeUpper(s)) {
            case "S": return Size.S;
            case "M": return Size.M;
            case "L": return Size.L;
            default:  return Size.UNKNOWN;
        }
    }

    private Extra parseExtra(String s) {
        switch (safeLower(s)) {
            case "milk":  return Extra.MILK;
            case "shot":  return Extra.SHOT;
            case "syrup": return Extra.SYRUP;
            default:      return Extra.UNKNOWN;
        }
    }

    private Coupon parseCoupon(String s) {
        if (s == null || s.trim().isEmpty()) return Coupon.NONE;
        switch (safeUpper(s)) {
            case "SAVE10":     return Coupon.SAVE10;
            case "FREEMUFFIN": return Coupon.FREEMUFFIN;
            default:           return Coupon.NONE;
        }
    }

    private String safeLower(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    private String safeUpper(String s) {
        return s == null ? "" : s.trim().toUpperCase();
    }
}