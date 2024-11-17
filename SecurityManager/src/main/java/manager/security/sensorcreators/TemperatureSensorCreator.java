package manager.security.sensorcreators;

import manager.security.sensors.Sensor;
import manager.security.sensors.TemperatureSensor;

import java.util.UUID;

public class TemperatureSensorCreator extends SensorCreator{
    @Override
    public Sensor CreateSensor(UUID floorId, UUID roomId) {
        return new TemperatureSensor(floorId, roomId);
    }
}
