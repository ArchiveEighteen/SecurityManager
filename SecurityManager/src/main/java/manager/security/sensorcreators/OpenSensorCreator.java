package manager.security.sensorcreators;

import logger.LogType;
import logger.Logger;
import manager.security.sensors.OpenSensor;
import manager.security.sensors.Sensor;

import java.util.UUID;

public class OpenSensorCreator extends SensorCreator{
    @Override
    public Sensor CreateSensor(UUID floorId, UUID roomId) {
        Logger.getInstance().log(floorId, roomId, "Open sensor was created", LogType.System);
        return new OpenSensor(floorId, roomId);
    }
}
