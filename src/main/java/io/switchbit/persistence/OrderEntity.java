package io.switchbit.persistence;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Type;

import io.switchbit.domain.Order;

@Entity(name = "t_order")
public class OrderEntity {

    public static final String NAME = "t_order";
    public static final String SEQUENCE_NAME = NAME + "_seq";

    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME)
    private Long id;

    private String customer;

    @Type(type = "Order")
    @Column(name = "order_xml")
    private Order order;

    OrderEntity() {
        // for JPA
    }

    public OrderEntity(final Order order) {
        this.customer = order.getCustomer();
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public Order getOrder() {
        return order;
    }
}
