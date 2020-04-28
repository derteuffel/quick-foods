package com.derteuffel.ecommerce.services;

import com.derteuffel.ecommerce.entities.OrderProduct;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface OrderProductService {

    OrderProduct create(@NotNull(message = "The products for order cannot be null.") @Validated OrderProduct orderProduct);
}
