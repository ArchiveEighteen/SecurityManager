package serializers.serializationclasses;

import manager.Room;
import manager.security.sensors.Sensor;

import java.util.List;
import java.util.UUID;

public class SerializationRoom extends Room {
    public SerializationRoom() {
        super();
    }

    @Override
    public void setId(UUID id) {
        super.setId(id);
    }

    @Override
    public void setFloorId(UUID floorId) {
        super.setFloorId(floorId);
    }

    @Override
    public void setWindowsAmount(int windowsAmount) {
        super.setWindowsAmount(windowsAmount);
    }

    @Override
    public void setDoorsAmount(int doorsAmount) {
        super.setDoorsAmount(doorsAmount);
    }

    @Override
    public void setArea(double area) {
        super.setArea(area);
    }

    @Override
    public void setSensors(List<Sensor> sensors) {
        super.setSensors(sensors);
    }
}
