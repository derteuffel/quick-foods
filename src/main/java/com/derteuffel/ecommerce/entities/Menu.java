package com.derteuffel.ecommerce.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String addedDate;
    private String slug;
    private String description;
    private String pictureUrl;

}
