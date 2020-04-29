package com.derteuffel.ecommerce.repositories;

import com.derteuffel.ecommerce.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

@Repository
public interface CommandeRepository extends JpaRepository<Commande,Long> {
}
