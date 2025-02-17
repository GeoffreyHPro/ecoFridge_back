package com.example.demo.payload;

import lombok.Data;

@Data
public class FoodRequest {
    private String bareCode;
    private String name;

    public FoodRequest(String bareCodeParam, String nameParam) {
        this.bareCode = bareCodeParam;
        this.name = nameParam;
    }
}
