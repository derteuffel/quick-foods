package com.derteuffel.ecommerce.repositories;

import com.derteuffel.ecommerce.entities.OrderProduct;
import com.derteuffel.ecommerce.entities.OrderProductPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPk> {
}
