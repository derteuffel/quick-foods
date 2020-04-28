package com.derteuffel.ecommerce.services;

import com.derteuffel.ecommerce.entities.Order;
import com.derteuffel.ecommerce.repositories.OrderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public @NotNull Collection<Order> getAllsOrders() {
        return this.orderRepository.findAll(Sort.by(Sort.Direction.DESC));
    }

    @Override
    public Order create(@NotNull(message = "The order can not be null.") @Valid Order order) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        order.setCreatedDate(format.format(LocalDate.now()));
        return this.orderRepository.save(order);
    }

    @Override
    public void update(@NotNull(message = "The order can not be null.") @Valid Order order) {
        this.orderRepository.save(order);
    }
}
