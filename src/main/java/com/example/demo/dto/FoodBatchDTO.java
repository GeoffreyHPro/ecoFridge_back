package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodBatchDTO {
    private int idFoodBatch;
    private int quantity;
    private LocalDateTime expirationDate;
    private String username;
    private FoodDTO food;
}
