package com.calendly.calendly_integration.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchedulingLinkRequest {
    @JsonProperty("max_event_count")
    private Integer maxEventCount;
    private String owner;
    @JsonProperty("owner_type")
    private String ownerType;
}
