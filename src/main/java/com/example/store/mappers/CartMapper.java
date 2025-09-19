package com.example.store.mappers;

import com.example.store.dtos.CartDto;
import com.example.store.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toDto(Cart cart);
}
