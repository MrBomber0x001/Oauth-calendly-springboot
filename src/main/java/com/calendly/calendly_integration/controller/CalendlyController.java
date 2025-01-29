package com.calendly.calendly_integration.controller;
import com.calendly.calendly_integration.dto.request.CreateSchedulingLinkRequest;
import com.calendly.calendly_integration.dto.response.EventTypeListResponse;
import com.calendly.calendly_integration.dto.response.SchedulingLinkResponse;
import com.calendly.calendly_integration.dto.response.UserResponse;
import com.calendly.calendly_integration.service.CalendlyAuthService;
import com.calendly.calendly_integration.service.CalendlyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@RestController
@RequiredArgsConstructor
public class CalendlyController {

    private final CalendlyAuthService authService;
    private final CalendlyService calendlyService;

    //1. generate Auth url -> used to begin the OAuth flow with Calendly
    @GetMapping("/auth/calendly")
    public String initiateAuth() {
        return authService.generateAuthUrl();
    }

    //2. get access token after recieving the code from the OAuth server
    @GetMapping("/auth/callback")
    public Mono<String> handleCallback(@RequestParam String code) {
        return authService.exchangeCodeForToken(code)
                .flatMap(token -> {
                    String accessToken = token.getAccessToken();
                    return calendlyService.getCurrentUser(accessToken)
                            .map(user -> String.format("Authentication successful!\nAccess Token: %s\nUser Email: %s",
                                    accessToken,
                                    user.getResource()));
                });
    }

    // Returns information about the current user
    @GetMapping("/me")
    public Mono<UserResponse> getCurrentUser(@RequestHeader("Authorization") String token) {

        System.out.println(token);
        return calendlyService.getCurrentUser(extractToken(token));
    }

    // Get user's event types passing the userUri
    @GetMapping("/event-types")
    public Mono<EventTypeListResponse> getEventTypes(
            @RequestHeader("Authorization") String token,
            @RequestParam String userUri) {
        return calendlyService.getEventTypes(extractToken(token), userUri);
    }


    @PostMapping("/scheduling-link")
    public Mono<SchedulingLinkResponse> createSchedulingLink(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateSchedulingLinkRequest request) {
        String accessToken = extractToken(token);
        return calendlyService.createSchedulingLink(accessToken, request);
    }





//     Get user's scheduled events
    /*@GetMapping("/scheduled-events")
    public Flux<EventTypeResponse> getScheduledEvents(
            @RequestHeader("Authorization") String token,
            @RequestParam String userUri,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime minStartTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime maxStartTime) {
        return calendlyService.getScheduledEvents(extractToken(token), userUri, minStartTime, maxStartTime);
    }*/

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Invalid authorization header");
    }
}
