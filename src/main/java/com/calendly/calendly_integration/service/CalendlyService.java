package com.calendly.calendly_integration.service;

import com.calendly.calendly_integration.dto.request.CreateSchedulingLinkRequest;
import com.calendly.calendly_integration.dto.response.AvailabilityResponse;
import com.calendly.calendly_integration.dto.response.EventTypeListResponse;
import com.calendly.calendly_integration.dto.response.SchedulingLinkResponse;
import com.calendly.calendly_integration.dto.response.UserResponse;
import com.calendly.calendly_integration.service.exception.CalendlyApiException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CalendlyService {
    private final WebClient calendlyWebClient;

    public CalendlyService(WebClient calendlyWebClient) {
        this.calendlyWebClient = calendlyWebClient;
    }

    public Mono<UserResponse> getCurrentUser(String accessToken) {
        return calendlyWebClient.get()
                .uri("/users/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new CalendlyApiException("Failed to get current user: " + error)))
                )
                .bodyToMono(UserResponse.class);
    }

    public Mono<EventTypeListResponse> getEventTypes(String accessToken, String userUri) {
        return calendlyWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/event_types")
                        .queryParam("user", userUri)
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new CalendlyApiException("Failed to get event types: " + error)))
                )
                .bodyToMono(EventTypeListResponse.class);
    }

    public Mono<AvailabilityResponse> getAvailability(String accessToken, String eventTypeUri) {
        return calendlyWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user_availability_schedules")
                        .queryParam("user", eventTypeUri)
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new CalendlyApiException("Failed to get availability: " + error)))
                )
                .bodyToMono(AvailabilityResponse.class);
    }

//    public Mono<ScheduledEventResponse> getScheduledEvents(
//            String accessToken,
//            String userUri,
//            ZonedDateTime minStartTime,
//            ZonedDateTime maxStartTime) {
//
//        return calendlyWebClient.get()
//                .uri(uriBuilder -> {
//                    uriBuilder.path("/scheduled_events")
//                            .queryParam("user", userUri);
//
//                    if (minStartTime != null) {
//                        uriBuilder.queryParam("min_start_time",
//                                minStartTime.format(DateTimeFormatter.ISO_DATE_TIME));
//                    }
//                    if (maxStartTime != null) {
//                        uriBuilder.queryParam("max_start_time",
//                                maxStartTime.format(DateTimeFormatter.ISO_DATE_TIME));
//                    }
//
//                    return uriBuilder.build();
//                })
//                .header("Authorization", "Bearer " + accessToken)
//                .retrieve()
//                .onStatus(
//                        status -> status.isError(),
//                        response -> response.bodyToMono(String.class)
//                                .flatMap(error -> Mono.error(
//                                        new CalendlyApiException("Failed to get scheduled events: " + error)))
//                )
//                .bodyToMono(ScheduledEventResponse.class);
//    }

    public Mono<SchedulingLinkResponse> createSchedulingLink(String accessToken, CreateSchedulingLinkRequest request) {
        return calendlyWebClient.post()
                .uri("/scheduling_links")
                .header("Authorization", "Bearer " + accessToken)
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new CalendlyApiException("Failed to create scheduling link: " + error)))
                )
                .bodyToMono(SchedulingLinkResponse.class);
    }
}