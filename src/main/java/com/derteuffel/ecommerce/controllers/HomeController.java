package com.derteuffel.ecommerce.controllers;

import com.derteuffel.ecommerce.entities.Commande;
import com.derteuffel.ecommerce.entities.Menu;
import com.derteuffel.ecommerce.entities.Panier;
import com.derteuffel.ecommerce.entities.Product;
import com.derteuffel.ecommerce.enums.OrderStatus;
import com.derteuffel.ecommerce.repositories.CommandeRepository;
import com.derteuffel.ecommerce.repositories.MenuRepository;
import com.derteuffel.ecommerce.repositories.PanierRepository;
import com.derteuffel.ecommerce.services.ProductService;
import org.springframework.data.domain.Sort;
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
    MenuRepository menuRepository;


    public HomeController(ProductService productService, CommandeRepository commandeRepository,
                          PanierRepository panierRepository, MenuRepository menuRepository) {
        this.productService = productService;
        this.commandeRepository=commandeRepository;
        this.panierRepository=panierRepository;
        this.menuRepository = menuRepository;
    }

    @GetMapping(value = {"","/"})
    public String home(HttpServletRequest request,Model model){
        System.out.println(request.getRequestURL());
        Panier panier = new Panier();
        panier.setStatus(OrderStatus.UNPAID.name());
        panierRepository.save(panier);
        List<Product> alls = productService.getAllsProduct();
        List<Menu> menus = new ArrayList<>();
        List<Menu> allsMenus = menuRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        List<Product> lists = new ArrayList<>();
        for (int i= 0;i<alls.size();i++){
            if (!(i>8)){
                lists.add(alls.get(i));
            }
        }

        for (int i = 0; i<allsMenus.size();i++){
            if (!(i>6)){
                menus.add(allsMenus.get(i));
            }
        }

        model.addAttribute("menus",menus);
        model.addAttribute("lists",lists);
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
        int n = 12;

        for (int i=0;i<alls.size();i++){
            if (!(i>n)) {
                lists.add(alls.get(i));
            }
        }

        model.addAttribute("lists",lists);
        return "category";
    }

    @GetMapping("/produits/location/{location}")
    public String getProductsByLocation(Model model, @PathVariable String location,HttpServletRequest request){
        request.getSession().setAttribute("lastUrl",request.getRequestURL());
        List<Product> alls = this.productService.findAllByLocationAndDisponibility(location);
        if (alls.size() == 0){
            model.addAttribute("message", "Product not found");
        }

        List<Product> lists = new ArrayList<>();
        int n = 12;

        for (int i=0;i<alls.size();i++){
            if (!(i>n)) {
                lists.add(alls.get(i));
            }
        }

        model.addAttribute("lists",lists);
        return "location";
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

    @GetMapping("/panier/{id}")
    public String panierDetails(@PathVariable Long id,Model model){
        Panier panier = panierRepository.getOne(id);
        List<Commande> commandes = commandeRepository.findAllByPanier_Id(panier.getId());
        System.out.println(commandes.size());
        Double num=0.0;
        panier.setAmount(panier.getAmount(commandes,num));
        panierRepository.save(panier);
        System.out.println(panier.getAmount());
        model.addAttribute("item",panier);
        model.addAttribute("commandes",commandes);
        return "panier";
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

    @GetMapping("/menus/detail/{id}")
    public String getMenu(@PathVariable Long id, Model model, HttpServletRequest request){
        request.getSession().setAttribute("lastUrl",request.getRequestURL());
        Menu menu = menuRepository.getOne(id);
        List<Menu> menus = this.menuRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<Menu> lists = new ArrayList<>();
        int n = 3;

        for (int i=0;i<menus.size();i++){
            if (!(i>n)) {
                lists.add(menus.get(i));
            }
        }
        model.addAttribute("menu", menu);
        model.addAttribute("lists",lists);
        return  "menu";

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
