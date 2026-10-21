#  ApexTelemetry — Formula 1 Telemetry Analyser

**A Java-based Formula 1 telemetry analysis tool that retrieves real motorsport data and calculates performance statistics.**

ApexTelemetry is a personal software engineering project designed to explore Formula 1 car performance through data analysis. Built with Java, the application retrieves telemetry from the OpenF1 API and analyses metrics such as speed, throttle input and braking behaviour.

The project is being developed to strengthen my skills in Java, object-oriented programming, data analysis and software testing, with a particular interest in motorsport and Formula 1 engineering.

##  Features

- **Real F1 telemetry:** Retrieve car data using the OpenF1 API.
- **Speed analysis:** Calculate maximum and average speed.
- **Throttle analysis:** Calculate average throttle input.
- **Braking analysis:** Count braking samples and estimate braking events.
- **Automated testing:** Validate analysis calculations using JUnit 5.
- **Modular architecture:** Separate API communication, data models and analysis logic.

##  Technologies

| Technology | Purpose |
|---|---|
| Java 21 | Core application |
| Maven | Dependency management and build automation |
| OpenF1 API | Formula 1 telemetry data |
| Jackson | JSON deserialisation |
| JUnit 5 | Automated unit testing |
| Git & GitHub | Version control |

##  Project Structure

```text
ApexTelemetry/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/apextelemetry/
    │   ├── Main.java
    │   ├── api/
    │   │   └── OpenF1Client.java
    │   ├── model/
    │   │   └── TelemetrySample.java
    │   └── analysis/
    │       └── TelemetryAnalyser.java
    └── test/java/com/apextelemetry/
        └── analysis/
            └── TelemetryAnalyserTest.java
```

##  Getting Started

### Prerequisites

- Java Development Kit (JDK) 21
- Maven
- An internet connection for retrieving telemetry

### Installation

1. Clone the repository:

   ```bash
   git clone YOUR_REPOSITORY_URL
   ```

2. Navigate into the project directory:

   ```bash
   cd ApexTelemetry
   ```

3. Compile the project:

   ```bash
   mvn clean compile
   ```

4. Open the project in IntelliJ IDEA, allow Maven to import the dependencies, and run `Main.java`.

### Running the Application

The application initially uses a configured OpenF1 session key and driver number.

The default configuration is:

- Session key: `9158`
- Driver number: `55`

These values can be changed in `Main.java` to analyse a different session or driver, provided the requested data is available through OpenF1.

The application retrieves telemetry and prints a summary containing speed, throttle and braking statistics.

##  Running Tests

Run the automated unit tests using:

```bash
mvn test
```

The tests use synthetic telemetry samples to verify statistical calculations, braking-event detection and input validation without requiring a live API connection.

##  Roadmap

The project is currently in its initial development stage.

- [x] Establish the Java and Maven project structure
- [x] Retrieve telemetry from the OpenF1 API
- [x] Calculate basic telemetry statistics
- [x] Implement initial braking-event detection
- [x] Add unit tests for core analysis logic
- [ ] Compare telemetry from two drivers
- [ ] Align laps by distance around the circuit
- [ ] Identify and compare braking zones
- [ ] Visualise telemetry using JavaFX charts
- [ ] Export analysis reports
- [ ] Add more advanced performance comparisons

##  Future Development

The long-term goal is to develop ApexTelemetry into a more comprehensive motorsport data analysis application.

Planned improvements include comparing drivers over a lap, visualising speed and throttle traces, identifying where performance differences occur, and generating reports from telemetry data.

These features will provide opportunities to explore algorithms, data processing, visualisation and software design in a motorsport context.

##  Limitations

- Braking events are estimates derived from sampled brake-state transitions.
- Telemetry is sampled at a finite rate, so brief events may not be captured.
- The current version calculates basic statistics and does not yet perform distance-aligned lap comparisons.
- Data availability depends on the OpenF1 API.

##  Data Source

Telemetry is retrieved from the [OpenF1 API](https://openf1.org/docs/), which provides access to Formula 1 timing and car data.

##  Author

A Computer Science undergraduate interested in Formula 1, motorsport engineering, Java development and data analysis.

ApexTelemetry is an independent learning and portfolio project.

---

*Built to explore the intersection of software engineering, data analysis and Formula 1.*
