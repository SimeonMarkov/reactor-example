package com.reactor.example.exception;

public class ZipNotFoundException extends RuntimeException {

    public ZipNotFoundException(String message) {
        super(message);
    }

}
