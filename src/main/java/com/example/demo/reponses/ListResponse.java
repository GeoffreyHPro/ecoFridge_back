package com.example.demo.reponses;

import java.util.List;

import lombok.Data;

@Data
public class ListResponse {
    private List data;

    public ListResponse(List data){
        this.data = data;
    }
}
