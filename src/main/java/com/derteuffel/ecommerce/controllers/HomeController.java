package com.derteuffel.ecommerce.controllers;

import com.derteuffel.ecommerce.entities.Product;
import com.derteuffel.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = {"","/"})
    public String home(){
        return "index";
    }


    @GetMapping("/produits")
    public String getProducts(Model model){
        Collection<Product> alls = productService.getAllsProduct();
        if (alls.size() == 0){
            model.addAttribute("message", "Product not found");
        }
        model.addAttribute("lists",alls);
        return "products";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }

    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }
}
