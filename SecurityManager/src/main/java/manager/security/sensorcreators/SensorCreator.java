package manager.security.sensorcreators;

import manager.security.sensors.Sensor;

import java.util.UUID;

public abstract class SensorCreator {
    public abstract Sensor CreateSensor(UUID floorId, UUID roomId);
}
