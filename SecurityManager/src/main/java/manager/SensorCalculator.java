package manager;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SensorCalculator {

    String requirementsFilePath = "Requirements.json";
    private SensorRequirements requirements;

    // Inner class to represent sensor requirements
    public static class SensorRequirements {
        public SensorType TemperatureSensor;
        public SensorType MotionSensor;
        public SensorType OpenSensor;

        public static class SensorType {
            public int areaPerSensor;
            public int doorsAndWindowsPerSensor;
        }
    }

    // Constructor: Reads requirements from JSON file
    public SensorCalculator() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.requirements = objectMapper.readValue(new File(requirementsFilePath), SensorRequirements.class);
    }

    // Method to calculate the number of sensors based on input parameters
    public SensorResult calculateSensors(double area, int numDoors, int numWindows) {
        int temperatureSensors = (int) Math.ceil(area / requirements.TemperatureSensor.areaPerSensor);
        int motionSensors = (int) Math.ceil(area / requirements.MotionSensor.areaPerSensor);
        int openSensors = (int) Math.ceil((double) (numDoors + numWindows) / requirements.OpenSensor.doorsAndWindowsPerSensor);

        return new SensorResult(temperatureSensors, motionSensors, openSensors);
    }

    // Inner class to hold the result
    public class SensorResult {
        private final int temperatureSensors;
        private final int motionSensors;
        private final int openSensors;

        public SensorResult(int temperatureSensors, int motionSensors, int openSensors) {
            this.temperatureSensors = temperatureSensors;
            this.motionSensors = motionSensors;
            this.openSensors = openSensors;
        }

        public int getTemperatureSensors() {
            return temperatureSensors;
        }
        public int getMotionSensors() {
            return motionSensors;
        }
        public int getOpenSensors() {
            return openSensors;
        }

        @Override
        public String toString() {
            return "SensorResult{" +
                    "temperatureSensors=" + temperatureSensors +
                    ", motionSensors=" + motionSensors +
                    ", openSensors=" + openSensors +
                    '}';
        }
    }
}