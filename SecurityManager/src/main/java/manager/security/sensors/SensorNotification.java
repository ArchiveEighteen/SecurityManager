package manager.security.sensors;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class SensorNotification {
    private final UUID sensorId;
    private final AtomicBoolean status;

    public SensorNotification(UUID sensorId, AtomicBoolean status) {
        this.sensorId = sensorId;
        this.status = status;
    }

    public UUID getSensorId() {
        return sensorId;
    }

    public boolean getStatus() {
        return status.get();
    }

    @Override
    public String toString() {
        return "SensorNotification{" +
                "sensorId='" + sensorId + '\'' +
                ", status=" + status +
                '}';
    }
}
