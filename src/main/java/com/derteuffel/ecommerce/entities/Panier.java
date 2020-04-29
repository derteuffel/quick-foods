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
    @OneToMany(mappedBy = "panier")
    private List<Commande> commandes = new ArrayList<>();

    @Transient
    public Double getAmount(){
        for (Commande commande : this.commandes){
            amount =+commande.getTotalAmount();
        }
        return amount;
    }

}
