package com.example.demo.payload;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FoodBatchRequest {
    private Integer quantity;
    private LocalDateTime expirationDate;

    public FoodBatchRequest(Integer quantity, LocalDateTime expirationDate) {
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }
}
