package com.apextelemetry.analysis;

import com.apextelemetry.model.TelemetrySample;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TelemetryAnalyser {
    public record Summary(
            int sampleCount,
            double maximumSpeed,
            double averageSpeed,
            double averageThrottle,
            long brakingSamples,
            int brakingEvents
    ) {}

    public Summary analyse(List<TelemetrySample> samples) {
        validateSamples(samples);

        double totalSpeed = 0.0;
        double totalThrottle = 0.0;
        double maximumSpeed = Double.NEGATIVE_INFINITY;
        long brakingSamples = 0;
        int brakingEvents = 0;
        boolean previouslyBraking = false;

        for (TelemetrySample sample : samples) {
            totalSpeed += sample.speed();
            totalThrottle += sample.throttle();
            maximumSpeed = Math.max(maximumSpeed, sample.speed());

            boolean braking = sample.brake() > 0;
            if (braking) {
                brakingSamples++;
            }
            if (braking && !previouslyBraking) {
                brakingEvents++;
            }
            previouslyBraking = braking;
        }

        return new Summary(
                samples.size(),
                maximumSpeed,
                totalSpeed / samples.size(),
                totalThrottle / samples.size(),
                brakingSamples,
                brakingEvents
        );
    }

    public List<TelemetrySample> findBrakingEvents(
            List<TelemetrySample> samples
    ) {
        validateSamples(samples);

        List<TelemetrySample> events = new ArrayList<>();
        boolean previouslyBraking = false;

        for (TelemetrySample sample : samples) {
            boolean braking = sample.brake() > 0;
            if (braking && !previouslyBraking) {
                events.add(sample);
            }
            previouslyBraking = braking;
        }

        return events;
    }

    private void validateSamples(List<TelemetrySample> samples) {
        Objects.requireNonNull(samples, "Telemetry samples cannot be null.");
        if (samples.isEmpty()) {
            throw new IllegalArgumentException(
                    "Telemetry samples cannot be empty."
            );
        }
        if (samples.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(
                    "Telemetry samples cannot contain null entries."
            );
        }
    }
}
