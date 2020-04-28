package com.derteuffel.ecommerce.controllers;

import com.derteuffel.ecommerce.entities.Order;
import com.derteuffel.ecommerce.entities.OrderProduct;
import com.derteuffel.ecommerce.entities.Product;
import com.derteuffel.ecommerce.enums.OrderStatus;
import com.derteuffel.ecommerce.helpers.OrderProductDto;
import com.derteuffel.ecommerce.services.OrderProductService;
import com.derteuffel.ecommerce.services.OrderService;
import com.derteuffel.ecommerce.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    ProductService productService;
    OrderService orderService;
    OrderProductService orderProductService;

    public HomeController(ProductService productService, OrderService orderService, OrderProductService orderProductService) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @GetMapping(value = {"","/"})
    public String home(){


        return "index";
    }

    @GetMapping("/produits")
    public String getProducts(Model model){
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

        model.addAttribute("form",new OrderForm());
        model.addAttribute("lists",lists);
        return "products";
    }

    @GetMapping("/produits/categories/{category}")
    public String getProductsByCategory(Model model, @PathVariable String category){
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

        model.addAttribute("form",new OrderForm());
        model.addAttribute("lists",lists);
        return "category";
    }




    @GetMapping("/orders")
    public String getAllsOrders(Model model){
        @NotNull Collection<Order> orders = this.orderService.getAllsOrders();
        model.addAttribute("form", new OrderForm());
        model.addAttribute("lists",orders);
        return "orders";
    }

    @GetMapping("/produits/{id}")
    public String getProduct(@PathVariable Long id, Model model){
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

    @PostMapping("/order/save")
    public String create(@Valid OrderForm form, RedirectAttributes redirectAttributes) {
        List<OrderProductDto> formDtos = form.getProductOrders();
        validateProductsExistence(formDtos);
        Order order = new Order();
        order.setStatus(OrderStatus.PAID.name());
        order = this.orderService.create(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto dto : formDtos) {
            orderProducts.add(orderProductService.create(new OrderProduct(order, productService.getProduct(dto
                    .getProduct()
                    .getId()), dto.getQuantity())));
        }

        order.setOrderProducts(orderProducts);

        this.orderService.update(order);
        redirectAttributes.addFlashAttribute("success","Commande reussie");
        return "redirect:/orders";
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

    private void validateProductsExistence(List<OrderProductDto> orderProducts) {
        List<OrderProductDto> list = orderProducts
                .stream()
                .filter(op -> Objects.isNull(productService.getProduct(op
                        .getProduct()
                        .getId())))
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(list)) {
            System.out.println("Product not found");
        }
    }

    public static class OrderForm {

        private List<OrderProductDto> productOrders;

        public List<OrderProductDto> getProductOrders() {
            return productOrders;
        }

        public void setProductOrders(List<OrderProductDto> productOrders) {
            this.productOrders = productOrders;
        }
    }
}
