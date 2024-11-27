package manager.security.sensors;

import logger.LogType;
import logger.Logger;

import java.util.UUID;

public class OpenSensor extends Sensor{

    public void detectBreach(){
        trigger();
        Logger.getInstance().log(this.floorId, roomId, "Breach detected", LogType.OpenSensor);
        // TODO: trigger alarm and log
    }
    public OpenSensor(UUID floorId, UUID sensorId) {
        super(SensorType.OpenSensor, floorId, sensorId);
    }

    public OpenSensor(UUID id, UUID floorId, UUID roomId, boolean status) {
        super(SensorType.OpenSensor, id, floorId, roomId, status);
    }
}
