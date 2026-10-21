package com.apextelemetry;

import com.apextelemetry.analysis.TelemetryAnalyser;
import com.apextelemetry.analysis.TelemetryAnalyser.Summary;
import com.apextelemetry.api.OpenF1Client;
import com.apextelemetry.model.TelemetrySample;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        int sessionKey = args.length > 0
                ? Integer.parseInt(args[0]) : 9158;
        int driverNumber = args.length > 1
                ? Integer.parseInt(args[1]) : 55;

        try {
            System.out.println("Fetching telemetry from OpenF1...");
            OpenF1Client client = new OpenF1Client();
            List<TelemetrySample> samples =
                    client.getCarData(sessionKey, driverNumber);

            if (samples.isEmpty()) {
                System.out.println(
                        "No telemetry was returned. Check the session key "
                                + "and driver number."
                );
                return;
            }

            TelemetryAnalyser analyser = new TelemetryAnalyser();
            Summary summary = analyser.analyse(samples);

            System.out.println();
            System.out.println("=== ApexTelemetry ===");
            System.out.println("Session key: " + sessionKey);
            System.out.println("Driver number: " + driverNumber);
            System.out.println("Samples: " + summary.sampleCount());
            System.out.printf(
                    "Maximum speed: %.1f km/h%n",
                    summary.maximumSpeed()
            );
            System.out.printf(
                    "Average speed: %.1f km/h%n",
                    summary.averageSpeed()
            );
            System.out.printf(
                    "Average throttle: %.1f%%%n",
                    summary.averageThrottle()
            );
            System.out.println(
                    "Braking samples: " + summary.brakingSamples()
            );
            System.out.println(
                    "Estimated braking events: " + summary.brakingEvents()
            );
            System.out.println();
            System.out.println(
                    "Note: braking-event counts are estimates from sampled "
                            + "brake-state transitions."
            );
        } catch (NumberFormatException e) {
            System.err.println(
                    "Usage: Main [sessionKey] [driverNumber]; both must be integers."
            );
        } catch (Exception e) {
            System.err.println(
                    "Could not analyse telemetry: " + e.getMessage()
            );
            System.err.println(
                    "Check your internet connection, session key, driver "
                            + "number and OpenF1 API availability."
            );
        }
    }
}
