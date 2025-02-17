package com.example.demo.error;

public class AlreadySavedError extends Exception {
    public AlreadySavedError() {
        super("Already saved");
    }
}
