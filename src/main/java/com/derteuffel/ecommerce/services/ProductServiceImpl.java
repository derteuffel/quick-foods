package com.derteuffel.ecommerce.services;

import com.derteuffel.ecommerce.repositories.ProductRepository;
import com.derteuffel.ecommerce.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> findOne(String name, String seller) {
        return productRepository.findByNameAndSeller(name,seller);
    }

    @Override
    public List<Product> findAllByLocationAndDisponibility(String location) {
        return this.productRepository.findAllByLocationAndDisponibility(location,true,Sort.by(Sort.Direction.DESC,"id"));
    }

    @Override
    public List<Product> getAllsProduct() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC,"name"));
    }

    @Override
    public List<Product> getAllsProductByCategories(String category) {
        return productRepository.findAllByCategoryAndDisponibility(category,true, Sort.by(Sort.Direction.DESC,"id"));
    }

    @Override
    public Product getProduct(Long id)  {
        return productRepository.getOne(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
}
