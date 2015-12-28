package io.switchbit.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Order {

    private String customer;

    @XmlElement(name = "orderItem")
    private List<OrderItem> orderItems = new ArrayList<>();

    Order() {
        // for JAXB
    }

    public Order(final String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(final List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(final OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }
}
