package manager;

import manager.security.sensorcreators.MotionSensorCreator;
import manager.security.sensorcreators.OpenSensorCreator;
import manager.security.sensorcreators.SensorCreator;
import manager.security.sensorcreators.TemperatureSensorCreator;
import manager.security.sensors.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {
    private final UUID id = UUID.randomUUID();
    private final UUID floorId;
    private int windowsAmount;
    private int doorsAmount;
    private double area;
    private final List<Sensor> sensors = new ArrayList<>();

    public Room(UUID floorId, int windowsAmount, int doorsAmount, double area) {
        this.floorId = floorId;

        UpdateRoomParameters(windowsAmount, doorsAmount, area);
    }

    public void UpdateRoomParameters(int windowsAmount, int doorsAmount, double area) {
        this.windowsAmount = windowsAmount;
        this.doorsAmount = doorsAmount;
        this.area = area;

        List<SensorCreator> sensorCreators = getSensorCreators(windowsAmount, doorsAmount, area);

        for(var sensorCreator : sensorCreators) {
            sensors.add(sensorCreator.CreateSensor(floorId, id));
        }
    }

    private List<SensorCreator> getSensorCreators(int windowsAmount, int doorsAmount, double area) {
        List<SensorCreator> sensorCreators = new ArrayList<>();

        // TODO: Read from file that contains standards for amount of sensors per room, create sensor creators
        // TODO: and add them to sensorCreators list

        // TODO: Delete section below after completing aforementioned task
        // start section
        sensorCreators.add(new TemperatureSensorCreator());
        for(int i = 0; i < doorsAmount+windowsAmount; i++) {
            sensorCreators.add(new OpenSensorCreator());
        }
        for(int i = 0; i < area / 15 + 1; i++) {
            sensorCreators.add(new MotionSensorCreator());
        }
        // end section

        return sensorCreators;
    }

    public UUID getId() {
        return id;
    }
    public UUID getFloorId() {
        return floorId;
    }
    public int getWindowsAmount() {
        return windowsAmount;
    }
    public int getDoorsAmount() {
        return doorsAmount;
    }
    public double getArea() {
        return area;
    }
    public List<Sensor> getSensors() {
        return sensors;
    }

}
