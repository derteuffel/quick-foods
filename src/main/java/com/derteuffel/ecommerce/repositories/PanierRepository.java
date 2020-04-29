package com.derteuffel.ecommerce.repositories;

import com.derteuffel.ecommerce.entities.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierRepository extends JpaRepository<Panier,Long> {
}
