package com.example.store.controllers;

import com.example.store.dtos.ProductDto;
import com.example.store.dtos.UpdateUserRequest;
import com.example.store.dtos.UserDto;
import com.example.store.entities.Product;
import com.example.store.mappers.ProductMapper;
import com.example.store.repositories.CategoryRepository;
import com.example.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

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
    // create Product
    //use existing product no need for another dto

    @PostMapping
    public ResponseEntity<ProductDto> createUser(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);

        if(category == null){
            return ResponseEntity.badRequest().build();
        }

        var product =  productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);

        productDto.setId(product.getId());

        var uri = uriBuilder.path("/product/{id}").buildAndExpand(productDto.getId()).toUri();

        return ResponseEntity.created(uri).body(productDto);
    }
    // update, create and delete
    // validate if product id, return 404 if not found
    // successfull return 204

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto  productDto) {

        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);

        if(category == null){
            return ResponseEntity.badRequest().build();
        }

        var product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return  ResponseEntity.notFound().build();
        }

        productMapper.update(productDto, product);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());

        return ResponseEntity.ok(productDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id){
        var product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return  ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }

}
