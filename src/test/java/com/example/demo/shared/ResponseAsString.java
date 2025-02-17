package com.example.demo.shared;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResponseAsString {
    private ObjectMapper mapper;

    public ResponseAsString() {
        this.mapper = new ObjectMapper();
    }

    public String getString(Object content) throws JsonProcessingException {
        String ojectJson = this.mapper.writeValueAsString(content);
        return ojectJson;
    }
}
