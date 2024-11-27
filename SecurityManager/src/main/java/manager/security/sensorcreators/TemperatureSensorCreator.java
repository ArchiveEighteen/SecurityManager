package manager.security.sensorcreators;

import logger.LogType;
import logger.Logger;
import manager.security.sensors.Sensor;
import manager.security.sensors.TemperatureSensor;

import java.util.UUID;

public class TemperatureSensorCreator extends SensorCreator{
    @Override
    public Sensor CreateSensor(UUID floorId, UUID roomId) {
        Logger.getInstance().log(floorId, roomId, "Temperature sensor was created", LogType.System);
        return new TemperatureSensor(floorId, roomId);
    }
}
