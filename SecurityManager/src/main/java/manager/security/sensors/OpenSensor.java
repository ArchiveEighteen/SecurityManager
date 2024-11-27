package manager.security.sensors;

import logger.LogType;
import logger.Logger;

import java.util.UUID;

public class OpenSensor extends Sensor{
    final private long sleepTime = 500;
    public void detectBreach(){
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
