package manager.security.sensors;

import java.util.UUID;

public class MotionSensor extends Sensor {

    public void detectMovement(){
        trigger();
        // TODO: turn on camera and log
    }
    public MotionSensor(UUID floorId, UUID sensorId) {
        super(SensorType.MotionSensor, floorId, sensorId);
    }

    public MotionSensor(UUID id, UUID floorId, UUID roomId, boolean status) {
        super(SensorType.MotionSensor, id, floorId, roomId, status);
    }
}
