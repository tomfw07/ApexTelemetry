package com.apextelemetry.api;

import com.apextelemetry.model.TelemetrySample;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class OpenF1Client {
    private static final String BASE_URL =
            "https://api.openf1.org/v1/car_data";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OpenF1Client() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public List<TelemetrySample> getCarData(
            int sessionKey,
            int driverNumber
    ) throws IOException, InterruptedException {
        String url = BASE_URL
                + "?session_key=" + sessionKey
                + "&driver_number=" + driverNumber;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new IOException(
                    "OpenF1 returned HTTP " + response.statusCode()
                            + ". Response: " + response.body()
            );
        }

        return objectMapper.readValue(
                response.body(),
                new TypeReference<List<TelemetrySample>>() {}
        );
    }
}
