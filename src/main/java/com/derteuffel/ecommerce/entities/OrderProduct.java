package com.derteuffel.ecommerce.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Data
@Entity
public class OrderProduct {

    @EmbeddedId
    private OrderProductPk pk;

    @Column(nullable = false)
    private Integer quantity;

    // default constructor

    public OrderProduct(Order order, Product product, Integer quantity) {
        pk = new OrderProductPk();
        pk.setOrder(order);
        pk.setProduct(product);
        this.quantity = quantity;
    }

    @Transient
    public Product getProduct() {
        return this.pk.getProduct();
    }

    @Transient
    public Double getTotalPrice() {
        return getProduct().getPrice() * getQuantity();
    }
}
