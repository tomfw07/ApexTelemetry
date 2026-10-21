package com.apextelemetry.analysis;

import com.apextelemetry.model.TelemetrySample;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TelemetryAnalyserTest {
    private final TelemetryAnalyser analyser = new TelemetryAnalyser();

    @Test
    void calculatesBasicStatistics() {
        List<TelemetrySample> samples = List.of(
                sample(200, 100, 0),
                sample(250, 80, 0),
                sample(150, 20, 100),
                sample(100, 0, 100),
                sample(180, 90, 0)
        );

        TelemetryAnalyser.Summary result = analyser.analyse(samples);

        assertEquals(5, result.sampleCount());
        assertEquals(250.0, result.maximumSpeed(), 0.001);
        assertEquals(176.0, result.averageSpeed(), 0.001);
        assertEquals(58.0, result.averageThrottle(), 0.001);
        assertEquals(2, result.brakingSamples());
        assertEquals(1, result.brakingEvents());
    }

    @Test
    void detectsSeparateBrakingEvents() {
        List<TelemetrySample> samples = List.of(
                sample(200, 100, 0),
                sample(180, 60, 100),
                sample(150, 20, 100),
                sample(160, 70, 0),
                sample(130, 10, 100)
        );

        assertEquals(2, analyser.findBrakingEvents(samples).size());
    }

    @Test
    void rejectsEmptyTelemetry() {
        assertThrows(
                IllegalArgumentException.class,
                () -> analyser.analyse(List.of())
        );
    }

    @Test
    void rejectsNullSamples() {
        assertThrows(
                NullPointerException.class,
                () -> analyser.analyse(null)
        );
    }

    private TelemetrySample sample(
            double speed,
            double throttle,
            int brake
    ) {
        return new TelemetrySample(
                "2023-09-15T13:00:00Z",
                55,
                9158,
                speed,
                throttle,
                brake,
                5,
                10000
        );
    }
}
