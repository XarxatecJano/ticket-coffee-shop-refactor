package src.main.java.bytebean.domain;

import java.util.List;

public record OrderItem(
    Product product,
    Size size,
    int quantity,
    List<Extra> extras
) {}
