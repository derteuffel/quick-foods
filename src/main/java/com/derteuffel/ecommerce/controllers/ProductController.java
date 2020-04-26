package com.derteuffel.ecommerce.controllers;

import com.derteuffel.ecommerce.entities.Product;
import com.derteuffel.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/lists")
    public String getAlls(Model model){
        Collection<Product> alls = productService.getAllsProduct();
        if (alls.size() == 0){
            model.addAttribute("message", "Product not found");
        }
        model.addAttribute("lists",alls);

        return "product/lists";
    }

}
