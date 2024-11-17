package manager.security.sensorcreators;

import manager.security.sensors.OpenSensor;
import manager.security.sensors.Sensor;

import java.util.UUID;

public class OpenSensorCreator extends SensorCreator{
    @Override
    public Sensor CreateSensor(UUID floorId, UUID roomId) {
        return new OpenSensor(floorId, roomId);
    }
}
