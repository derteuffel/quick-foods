package com.derteuffel.ecommerce.repositories;

import com.derteuffel.ecommerce.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryAndDisponibility(String category, Boolean disponibility, Sort sort);
    Optional<Product> findByNameAndSeller(String name, String seller);
    List<Product> findAllByLocationAndDisponibility(String location, Boolean disponibility, Sort sort);
}
