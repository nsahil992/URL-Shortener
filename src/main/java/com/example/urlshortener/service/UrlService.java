package com.example.urlshortener.service;

import com.example.urlshortener.util.ExpiryDuration;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {

    String shortenUrl(String originalUrl, ExpiryDuration duration);

    String getOriginalUrl(String shortCode);
}
