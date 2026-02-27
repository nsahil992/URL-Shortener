package com.example.urlshortener.service;

import org.springframework.stereotype.Service;

@Service
public interface UrlService {

    String shortenUrl(String originalUrl);

    String getOriginalUrl(String shortCode);
}
