package com.derteuffel.ecommerce;

import com.derteuffel.ecommerce.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Collection<Product> findAllByCategoryAndDisponibility(String category, Boolean disponibility, Sort sort);
    Optional<Product> findByNameAndSeller(String name, String seller);
}
