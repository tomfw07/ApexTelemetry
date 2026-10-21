package com.apextelemetry.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TelemetrySample(
        String date,
        @JsonProperty("driver_number") int driverNumber,
        @JsonProperty("session_key") int sessionKey,
        double speed,
        double throttle,
        int brake,
        @JsonProperty("n_gear") int gear,
        int rpm
) {
}
