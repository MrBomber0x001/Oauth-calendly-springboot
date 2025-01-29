package com.calendly.calendly_integration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedulingLinkResponse {
    private SchedulingLinkResource resource;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SchedulingLinkResource {
        @JsonProperty("booking_url")
        private String bookingUrl;
        private String owner;
        @JsonProperty("owner_type")
        private String ownerType;
    }
}