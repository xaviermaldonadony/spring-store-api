package com.example.store.validation;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.example.store.dtos.OrderDto;
import com.example.store.exceptions.OrderNotFoundException;
import com.example.store.mappers.OrderMapper;
import com.example.store.repositories.OrderRepository;
import com.example.store.services.AuthService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(user);

        return orders.stream().map(orderMapper::toDto).toList();
    }


    public OrderDto getOrder(Long orderId) {
        var order = orderRepository
                .getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        var user = authService.getCurrentUser();
        if(!order.isPlacedBy(user)){
            throw new AccessDeniedException("You don't have permission to access this order");
        }

        return orderMapper.toDto(order);
    }
}