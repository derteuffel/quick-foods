package com.derteuffel.ecommerce.controllers;

import com.derteuffel.ecommerce.entities.Product;
import com.derteuffel.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Value("${file.upload-dir}")
    private String fileStorage;

    @Autowired
    private ProductService productService;

    @GetMapping("/product/lists")
    public String getAlls(Model model){
        Collection<Product> alls = productService.getAllsProduct();
        if (alls.size() == 0){
            model.addAttribute("message", "Product not found");
        }
        model.addAttribute("lists",alls);
        model.addAttribute("product", new Product());

        return "product/lists";
    }

    @PostMapping("/product/save")
    public String saveProduct(@Valid Product product, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, String price){

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Optional<Product> optional = productService.findOne(product.getName().toUpperCase(),product.getSeller().toLowerCase());
        if (optional.isPresent()){
            optional.get().setQuantity(optional.get().getQuantity()+product.getQuantity());
            optional.get().setPrice(product.getPrice());
            optional.get().setAddedDate(format.format(date));
            if (optional.get().getDisponibility() == false){
                optional.get().setDisponibility(true);
            }
            productService.saveProduct(optional.get());
        }else {
            if (!(file.isEmpty())) {
                try {
                    // Get the file and save it somewhere
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(fileStorage + file.getOriginalFilename());
                    Files.write(path, bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                product.setPictureUrl("/downloadFile/" + file.getOriginalFilename());
            }
            product.setAddedDate(format.format(date));
            product.setPrice(Double.parseDouble(price));
            product.setDisponibility(true);
            productService.saveProduct(product);
        }
        redirectAttributes.addFlashAttribute("success", "Votre produit a ete ajouter avec succes");
        return "redirect:/admin/product/lists";
    }

}
