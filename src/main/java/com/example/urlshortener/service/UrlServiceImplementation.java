package com.example.urlshortener.service;

import com.example.urlshortener.exception.ShortUrlNotFoundException;
import com.example.urlshortener.exception.UrlExpiredException;
import com.example.urlshortener.repository.UrlRecordRepository;
import com.example.urlshortener.util.ExpiryDuration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlServiceImplementation implements UrlService {

    private final UrlRecordRepository urlRecordRepository;

    public UrlServiceImplementation(UrlRecordRepository urlRecordRepository) {
        this.urlRecordRepository = urlRecordRepository;
    }

    // Generate Short Code
    private String generateShortCode() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortCode = new StringBuilder();
        int length = 6;

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            shortCode.append(characters.charAt(index));
        }

        return shortCode.toString();
    }

    @Override
    public String shortenUrl(String originalUrl, ExpiryDuration duration) {

        // If URL already exists
        var existingUrl = urlRecordRepository.findByOriginalUrl(originalUrl);
        if (existingUrl.isPresent()) {
            return existingUrl.get().getShortCode();
        }

        // Generate short code
        String shortCode = generateShortCode();

        // Save in DB
        var urlRecord = com.example.urlshortener.entity.UrlRecord.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .createdAt(java.time.LocalDateTime.now())
                .clickCount(0L)
                .expiresAt(calculateExpiry(duration))
                .build();

        urlRecordRepository.save(urlRecord);

        return shortCode;
    }

    @Override
    public String getOriginalUrl(String shortCode) {

        var recordOptional = urlRecordRepository.findByShortCode(shortCode);

        if (recordOptional.isEmpty()) {
            throw new ShortUrlNotFoundException("Short URL not found");
        }

        var record = recordOptional.get();

        if (record.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new UrlExpiredException("Short URL has expired");
        }

        // Increment the click count
        record.setClickCount(record.getClickCount() + 1);
        urlRecordRepository.save(record);
        return record.getOriginalUrl();
    }

    private LocalDateTime calculateExpiry(com.example.urlshortener.util.ExpiryDuration duration) {
        LocalDateTime now = LocalDateTime.now();

        return switch (duration) {
            case ONE_HOUR ->  now.plusHours(1);
            case ONE_DAY -> now.plusDays(1);
            case SEVEN_DAYS -> now.plusDays(7);
            case THIRTY_DAYS -> now.plusDays(30);
        };
    }
}
