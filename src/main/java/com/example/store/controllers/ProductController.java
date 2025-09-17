package com.example.store.controllers;

import com.example.store.dtos.ProductDto;
import com.example.store.entities.Product;
import com.example.store.mappers.ProductMapper;
import com.example.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public Iterable<ProductDto> getAllProducts(
            @RequestParam(required = false, name = "categoryId") Byte categoryId) {


        List<Product> products = categoryId != null ?
                productRepository.findByCategoryId(categoryId) :
                productRepository.findAllWithCategory();

        return products.stream().map(productMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);

        if (product == null) {
            // 404
            return ResponseEntity.notFound().build();
        }

        var productDto = productMapper.toDto(product);

        return ResponseEntity.ok(productDto);
    }
}
