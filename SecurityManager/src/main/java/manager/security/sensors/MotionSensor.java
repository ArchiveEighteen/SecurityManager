package manager.security.sensors;

import java.util.UUID;

public class MotionSensor extends Sensor {

    public void detectMovement(){
        trigger();
        // TODO: turn on camera and log
    }
    public MotionSensor(UUID floorId, UUID sensorId) {
        super(floorId, sensorId);
    }
}
