package com.example.urlshortener.repository;

import com.example.urlshortener.entity.UrlRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRecordRepository extends JpaRepository<UrlRecord, Long> {

    Optional<UrlRecord> findByShortCode(String shortCode);

    Optional<UrlRecord> findByOriginalUrl(String url);
}
