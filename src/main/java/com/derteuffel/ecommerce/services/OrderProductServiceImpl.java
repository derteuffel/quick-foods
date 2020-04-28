package com.derteuffel.ecommerce.services;

import com.derteuffel.ecommerce.entities.OrderProduct;
import com.derteuffel.ecommerce.repositories.OrderProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService{

    private OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }
    @Override
    public OrderProduct create(@NotNull(message = "The products for order cannot be null.") OrderProduct orderProduct) {
        return this.orderProductRepository.save(orderProduct);
    }
}
