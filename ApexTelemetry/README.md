# ApexTelemetry

A Java 21 Maven starter project for analysing Formula 1 car telemetry using the OpenF1 API.

## Features
- Fetch car telemetry for a session and driver
- Calculate maximum/average speed, average throttle and braking statistics
- Detect estimated braking-event starts
- Unit tests using synthetic telemetry (no internet required)

## Requirements
- JDK 21
- Maven (or IntelliJ IDEA with Maven support)
- Internet connection to retrieve live API data

## Run in IntelliJ
1. Open this folder as a project.
2. Ensure the project SDK is Java 21.
3. Wait for Maven to import dependencies.
4. Open `src/main/java/com/apextelemetry/Main.java` and run `main`.

The default example uses OpenF1 session key `9158` and driver number `55`. If that session is unavailable, consult https://openf1.org/docs/ for current endpoint parameters and session keys.

## Run tests
```bash
mvn test
```

## Important limitations
- The first implementation analyses raw samples; it does not yet align two laps by distance.
- Braking events are estimates based on brake-state transitions and can be affected by sample rate or noisy data.
- The API response and availability may change. Check HTTP errors if the request fails.
- OpenF1 data is subject to its own terms and availability.

## Suggested next features
1. Add session/driver selection and date filters.
2. Implement distance-based lap alignment.
3. Add a second-driver comparison.
4. Add JavaFX charts.
5. Export reports and add integration tests.
