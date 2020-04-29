package com.derteuffel.ecommerce.controllers;

import com.derteuffel.ecommerce.services.ProductService;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    ProductService productService;


    public OrderController(ProductService productService) {
        this.productService = productService;
    }


    public String form(){
        return "";
    }
}
