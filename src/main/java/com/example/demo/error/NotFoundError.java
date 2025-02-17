package com.example.demo.error;

public class NotFoundError extends Exception{
    public NotFoundError() {
        super("Ressource not found");
    }
}
