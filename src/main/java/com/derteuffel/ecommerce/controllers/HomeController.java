package com.derteuffel.ecommerce.controllers;

import com.derteuffel.ecommerce.entities.Commande;
import com.derteuffel.ecommerce.entities.Panier;
import com.derteuffel.ecommerce.entities.Product;
import com.derteuffel.ecommerce.enums.OrderStatus;
import com.derteuffel.ecommerce.repositories.CommandeRepository;
import com.derteuffel.ecommerce.repositories.PanierRepository;
import com.derteuffel.ecommerce.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HomeController {

    ProductService productService;
    PanierRepository panierRepository;
    CommandeRepository commandeRepository;


    public HomeController(ProductService productService, CommandeRepository commandeRepository, PanierRepository panierRepository) {
        this.productService = productService;
        this.commandeRepository=commandeRepository;
        this.panierRepository=panierRepository;
    }

    @GetMapping(value = {"","/"})
    public String home(HttpServletRequest request){
        System.out.println(request.getRequestURL());
        Panier panier = new Panier();
        panier.setStatus(OrderStatus.UNPAID.name());
        panierRepository.save(panier);
        request.getSession().setAttribute("lastUrl",request.getRequestURL());
        request.getSession().setAttribute("panier",panier);
        return "index";
    }

    @GetMapping("/produits")
    public String getProducts(Model model,HttpServletRequest request){
        request.getSession().setAttribute("lastUrl",request.getRequestURL());
        List<Product> alls = productService.getAllsProduct();
        if (alls.size() == 0){
            model.addAttribute("message", "Product not found");
        }

        List<Product> lists = new ArrayList<>();
        int n = 8;

        for (int i=0;i<alls.size();i++){
            if (!(i>n)) {
                lists.add(alls.get(i));
            }
        }

        model.addAttribute("lists",lists);
        return "products";
    }

    @GetMapping("/produits/categories/{category}")
    public String getProductsByCategory(Model model, @PathVariable String category,HttpServletRequest request){
        request.getSession().setAttribute("lastUrl",request.getRequestURL());
        List<Product> alls = this.productService.getAllsProductByCategories(category);
        if (alls.size() == 0){
            model.addAttribute("message", "Product not found");
        }

        List<Product> lists = new ArrayList<>();
        int n = 8;

        for (int i=0;i<alls.size();i++){
            if (!(i>n)) {
                lists.add(alls.get(i));
            }
        }

        model.addAttribute("lists",lists);
        return "category";
    }


    @GetMapping("/add/panier/{id}")
    public String newPanier(@PathVariable Long id, int quantity, HttpServletRequest request){

        System.out.println("vous avez choisis : "+ quantity +" elements");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Product product = productService.getProduct(id);
        Panier panier = (Panier) request.getSession().getAttribute("panier");
        System.out.println(panier.getStatus());
        Commande commande = new Commande();
        commande.setCreatedDate(format.format(new Date()));
        commande.setQuantity(quantity);
        commande.setProduct(product);
        commande.setPanier(panier);
        System.out.println("je suis la");
        commandeRepository.save(commande);
        System.out.println(request.getSession().getAttribute("lastUrl"));
    return "redirect:"+request.getSession().getAttribute("lastUrl");

    }






    @GetMapping("/produits/{id}")
    public String getProduct(@PathVariable Long id, Model model, HttpServletRequest request){
        request.getSession().setAttribute("lastUrl",request.getRequestURL());
        Product product = this.productService.getProduct(id);
        List<Product> products = this.productService.getAllsProductByCategories(product.getCategory());
        List<Product> lists = new ArrayList<>();
        int n = 5;

        for (int i=0;i<products.size();i++){
            if (!(i>n)) {
                lists.add(products.get(i));
            }
        }
        model.addAttribute("product", product);
        model.addAttribute("lists",lists);
        return  "product";

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
