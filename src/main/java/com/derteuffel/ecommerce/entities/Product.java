package com.derteuffel.ecommerce.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Table(name = "product")
@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "product name is required.")
    @Basic(optional = false)
    private String name;
    @NotNull(message = "product price is required.")
    private Double price;
    @NotNull(message = "product seller is required.")
    private String seller;
    @NotNull(message = "product quantity can not be less than 1.")
    @Min(value = 1)
    private int quantity = 1;
    private String addedDate;

    @NotNull(message = "product category is required.")
    private String category;
    @NotNull(message = "product location is required.")
    private String location;
    private Boolean disponibility;
    private String description;

    private String pictureUrl;


}
