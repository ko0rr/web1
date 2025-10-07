package org.example.backend.exceptions;

public class UnsupportedHttpException extends Exception {
    public UnsupportedHttpException(String message) {
        super(message);
    }
}