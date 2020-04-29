package com.derteuffel.ecommerce.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "commande")
public class Commande implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String createdDate;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Panier panier;
    private Integer quantity;

    @Transient
    public Double getTotalAmount(){
        return this.getProduct().getPrice() * getQuantity();
    }
}
