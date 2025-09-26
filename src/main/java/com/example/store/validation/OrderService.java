package com.example.store.validation;

import com.example.store.dtos.OrderDto;
import com.example.store.mappers.OrderMapper;
import com.example.store.repositories.OrderRepository;
import com.example.store.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getAllByCustomer(user);

        return orders.stream().map(orderMapper::toDto).toList();
    }
}
