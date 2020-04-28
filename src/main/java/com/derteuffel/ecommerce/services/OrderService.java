package com.derteuffel.ecommerce.services;

import com.derteuffel.ecommerce.entities.Order;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface OrderService {

    @NotNull Collection<Order> getAllsOrders();
    Order create(@NotNull(message = "The order can not be null.") @Valid Order order);
    void update(@NotNull(message = "The order can not be null.") @Valid Order order);
}
