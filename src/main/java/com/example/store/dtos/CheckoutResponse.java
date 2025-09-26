package com.example.store.dtos;

import lombok.Data;

@Data
public class CheckoutResponse {
    private Long orderId;

    public CheckoutResponse(Long id) {
        this.orderId = id;
    }
}
