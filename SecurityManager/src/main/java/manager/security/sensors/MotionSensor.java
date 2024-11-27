package manager.security.sensors;

import logger.LogType;
import logger.Logger;

import java.util.UUID;

public class MotionSensor extends Sensor {
    final private long sleepTime = 500;
    public void detectMovement(){
        trigger();

        Thread stopTheNigga = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    Logger.getInstance().log(floorId,roomId, e.getMessage(), LogType.System);//throw new RuntimeException(e);
                }
                reset();
                System.out.println("nigga executed");
            }
        };
        stopTheNigga.start();

        Logger.getInstance().log(this.floorId, roomId, "Movement detected", LogType.MotionSensor);
        // TODO: turn on camera and log
    }
    public MotionSensor(UUID floorId, UUID sensorId) {
        super(SensorType.MotionSensor, floorId, sensorId);
    }

    public MotionSensor(UUID id, UUID floorId, UUID roomId, boolean status) {
        super(SensorType.MotionSensor, id, floorId, roomId, status);
    }
}
