package com.derteuffel.ecommerce.services;

import com.derteuffel.ecommerce.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Validated
public interface ProductService {

    List<Product> getAllsProduct();
    List<Product> getAllsProductByCategories(String category);
    Product getProduct(Long id) ;
    Product saveProduct(Product product);
    Product updateProduct(Product product);
    Optional<Product> findOne(String name, String seller);
    List<Product> findAllByLocationAndDisponibility(String location);

}
