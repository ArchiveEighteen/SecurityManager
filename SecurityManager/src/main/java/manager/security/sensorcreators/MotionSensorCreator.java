package manager.security.sensorcreators;

import logger.LogType;
import logger.Logger;
import manager.security.sensors.MotionSensor;
import manager.security.sensors.Sensor;

import java.util.UUID;

public class MotionSensorCreator extends SensorCreator{

    @Override
    public Sensor CreateSensor(UUID floorId, UUID roomId) {
        Logger.getInstance().log(floorId, roomId, "Motion sensor was created", LogType.System);
        return new MotionSensor(floorId, roomId);
    }
}
