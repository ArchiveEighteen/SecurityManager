package manager.security.sensors;

import logger.LogType;
import logger.Logger;

import java.util.UUID;

public class TemperatureSensor extends Sensor {
    private double currentTemperature;
    private double maxTemperature = 35.0; // TODO: remove initialization after completing constructor
    private double minTemperature = 10.0; // TODO: remove initialization after completing constructor

    public void changeTemperature(double temperature) {
        if (currentTemperature > maxTemperature && temperature < maxTemperature) {
            reset();
            Logger.getInstance().log(this.floorId, roomId, "Temperature is normal", LogType.TemperatureSensor);
            // TODO: Log normalization
        } else if (currentTemperature < minTemperature && temperature > minTemperature) {
            reset();
            Logger.getInstance().log(this.floorId, roomId, "Temperature is normal", LogType.TemperatureSensor);
            // TODO: Log normalization
        }
        currentTemperature = temperature;
        if (currentTemperature > maxTemperature) {
            trigger();
            Logger.getInstance().log(this.floorId, roomId, "Temperature is higher than normal", LogType.TemperatureSensor);
            // TODO: Decrease temperature and log
        } else if (currentTemperature < minTemperature) {
            trigger();
            Logger.getInstance().log(this.floorId, roomId, "Temperature is lower than normal", LogType.TemperatureSensor);
            // TODO: Increase temperature and log
        }
    }

    public TemperatureSensor(UUID floorId, UUID roomId) {
        super(SensorType.TemperatureSensor, floorId, roomId);

        // TODO: Set min and max temperature. Get values from file that contains standards
    }

    public TemperatureSensor(UUID id, UUID floorId, UUID roomId, boolean status, double currentTemperature,
                                double minTemperature, double maxTemperature) {
        super(SensorType.TemperatureSensor, id, floorId, roomId, status);
        this.currentTemperature = currentTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }
}
