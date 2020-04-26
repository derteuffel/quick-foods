package com.derteuffel.ecommerce.services;

import com.derteuffel.ecommerce.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface ProductService {

    Collection<Product> getAllsProduct();
    Collection<Product> getAllsProductByCategories(String category);
    Product getProduct(Long id) ;
    Product saveProduct(Product product);
    Product updateProduct(Product product);
    Optional<Product> findOne(String name, String seller);

}
