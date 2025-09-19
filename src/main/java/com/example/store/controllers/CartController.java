package com.example.store.controllers;

import com.example.store.dtos.CartDto;
import com.example.store.entities.Cart;
import com.example.store.mappers.CartMapper;
import com.example.store.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder){
        var cart = new Cart();
        cartRepository.save(cart);

        CartDto cartDto = cartMapper.toDto(cart);
        var uri = uriBuilder.path("/carts/{id").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }
}
