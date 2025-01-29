package com.calendly.calendly_integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean(name = "calendlyWebClient")
    public WebClient calendlyWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.calendly.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean(name = "authWebClient")
    public WebClient authWebClient() {
        return WebClient.builder()
                .baseUrl("https://auth.calendly.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}