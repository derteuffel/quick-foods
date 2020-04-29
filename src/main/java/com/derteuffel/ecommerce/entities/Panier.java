package com.derteuffel.ecommerce.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "panier")
public class Panier implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String status;
    private Double amount;

    @Transient
    public Double getAmount(List<Commande> commandes, Double total){
        for (Commande commande : commandes){
            total +=commande.getTotalAmount();
        }
        return total;
    }

}
