package com.example.demo.payload;

import lombok.Data;

@Data
public class FoodUpdateRequest {
    private String name;
    private String description;

    public FoodUpdateRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
