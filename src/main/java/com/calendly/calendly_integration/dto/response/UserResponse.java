package com.calendly.calendly_integration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.ZonedDateTime;



@Data
class UserResource {
    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @JsonProperty("current_organization")
    private String currentOrganization;

    private String email;
    private String locale;
    private String name;

    @JsonProperty("resource_type")
    private String resourceType;

    @JsonProperty("scheduling_url")
    private String schedulingUrl;

    private String slug;
    private String timezone;

    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;

    private String uri;
}

@Data
public class UserResponse {
    private UserResource resource;
}