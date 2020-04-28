package com.derteuffel.ecommerce.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String raw;
}
