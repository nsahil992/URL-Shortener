package com.example.urlshortener.service;

import com.example.urlshortener.exception.ShortUrlNotFoundException;
import com.example.urlshortener.repository.UrlRecordRepository;
import org.springframework.stereotype.Service;

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
    public String shortenUrl(String originalUrl) {

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
                .build();

        urlRecordRepository.save(urlRecord);

        return shortCode;
    }

    @Override
    public String getOriginalUrl(String shortCode) {

        var record = urlRecordRepository.findByShortCode(shortCode);

        if (record.isEmpty()) {
            throw new ShortUrlNotFoundException("Short URL not found");
        }

        return record.get().getOriginalUrl();
    }
}
