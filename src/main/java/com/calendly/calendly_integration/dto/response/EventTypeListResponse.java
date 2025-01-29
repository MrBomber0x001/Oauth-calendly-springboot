package com.calendly.calendly_integration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class EventTypeListResponse {
    private List<EventTypeResource> collection;
    private Pagination pagination;

    @Data
    public static class EventTypeResource {
        private String uri;
        private String name;
        private boolean active;

        @JsonProperty("booking_method")
        private String bookingMethod;

        private String slug;

        @JsonProperty("scheduling_url")
        private String schedulingUrl;

        private int duration;

        @JsonProperty("duration_options")
        private List<Integer> durationOptions;

        private String kind;

        @JsonProperty("pooling_type")
        private String poolingType;

        private String type;
        private String color;

        @JsonProperty("created_at")
        private String createdAt;  // Use String if you don't need ZonedDateTime

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("internal_note")
        private String internalNote;

        @JsonProperty("description_plain")
        private String descriptionPlain;

        @JsonProperty("description_html")
        private String descriptionHtml;

        private Profile profile;
        private boolean secret;

        @JsonProperty("deleted_at")
        private String deletedAt;

        @JsonProperty("admin_managed")
        private boolean adminManaged;

        private List<Location> locations;
        private int position;

        @JsonProperty("custom_questions")
        private List<CustomQuestion> customQuestions;

        @Data
        public static class Profile {
            private String type;
            private String name;
            private String owner;
        }

        @Data
        public static class Location {
            private String kind;

            @JsonProperty("phone_number")
            private Long phoneNumber;

            @JsonProperty("additional_info")
            private String additionalInfo;
        }

        @Data
        public static class CustomQuestion {
            private String name;
            private String type;
            private int position;
            private boolean enabled;
            private boolean required;

            @JsonProperty("answer_choices")
            private List<String> answerChoices;

            @JsonProperty("include_other")
            private boolean includeOther;
        }
    }

    @Data
    public static class Pagination {
        private int count;

        @JsonProperty("next_page")
        private String nextPage;

        @JsonProperty("previous_page")
        private String previousPage;

        @JsonProperty("next_page_token")
        private String nextPageToken;

        @JsonProperty("previous_page_token")
        private String previousPageToken;
    }
}