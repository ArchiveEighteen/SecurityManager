package manager.security.sensors;

import java.util.UUID;

public class OpenSensor extends Sensor{

    public void detectBreach(){
        trigger();
        // TODO: trigger alarm and log
    }
    public OpenSensor(UUID floorId, UUID sensorId) {
        super(floorId, sensorId);
    }
}
