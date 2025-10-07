package org.example.backend.exceptions;

public class UnsupportedRequestException extends Exception {
    public UnsupportedRequestException() {
    }
    public UnsupportedRequestException(String message) {
        super(message);
    }
}