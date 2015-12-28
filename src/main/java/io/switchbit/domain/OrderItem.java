package io.switchbit.domain;

public class OrderItem {

    private String sku;
    private Double price;

    OrderItem() {
        // for JAXB
    }

    public OrderItem(final String sku, final Double price) {
        this.sku = sku;
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public Double getPrice() {
        return price;
    }
}
