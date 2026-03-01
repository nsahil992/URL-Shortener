package com.example.urlshortener.exception;

import java.time.LocalDateTime;

public class UrlExpiredException extends RuntimeException {
    public UrlExpiredException(String message) {
        super(message);
    }
}
