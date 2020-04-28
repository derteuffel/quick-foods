package com.derteuffel.ecommerce.helpers;

import com.derteuffel.ecommerce.entities.Product;
import lombok.Data;

@Data
public class OrderProductDto {

    private Product product;
    private Integer quantity;

}
