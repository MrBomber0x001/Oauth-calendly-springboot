package com.calendly.calendly_integration.service;

import com.calendly.calendly_integration.dto.response.TokenResponse;
import com.calendly.calendly_integration.service.exception.CalendlyApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CalendlyAuthService {
    private final WebClient authWebClient;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public CalendlyAuthService(
            WebClient authWebClient,
            @Value("${calendly.client-id}") String clientId,
            @Value("${calendly.client-secret}") String clientSecret,
            @Value("${calendly.redirect-uri}") String redirectUri) {
        this.authWebClient = authWebClient;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public String generateAuthUrl() {
        return "https://auth.calendly.com/oauth/authorize?" +
                "client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=default";
    }

    public Mono<TokenResponse> exchangeCodeForToken(String code) {
        return authWebClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(
                        "client_id=" + clientId +
                                "&client_secret=" + clientSecret +
                                "&code=" + code +
                                "&grant_type=authorization_code" +
                                "&redirect_uri=" + redirectUri
                )
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new CalendlyApiException("Token exchange error: " + error)))
                )
                .bodyToMono(TokenResponse.class);
    }
}