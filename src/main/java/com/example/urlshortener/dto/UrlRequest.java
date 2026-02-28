package com.example.urlshortener.dto;

import com.example.urlshortener.util.ExpiryDuration;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UrlRequest {
    @NotBlank(message = "URL cannot be empty")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "URL must start with http:// or https://"
    )
    private String originalUrl;

    @NotNull(message = "Expiry duration is needed")
    private ExpiryDuration expiryDuration;
}
