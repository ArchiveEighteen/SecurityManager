package manager.security.sensorcreators;

import manager.security.sensors.MotionSensor;
import manager.security.sensors.Sensor;

import java.util.UUID;

public class MotionSensorCreator extends SensorCreator{

    @Override
    public Sensor CreateSensor(UUID floorId, UUID roomId) {
        return new MotionSensor(floorId, roomId);
    }
}
