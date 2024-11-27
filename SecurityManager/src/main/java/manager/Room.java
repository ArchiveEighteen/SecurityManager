package manager;

import logger.LogType;
import logger.Logger;
import manager.security.sensorcreators.MotionSensorCreator;
import manager.security.sensorcreators.OpenSensorCreator;
import manager.security.sensorcreators.SensorCreator;
import manager.security.sensorcreators.TemperatureSensorCreator;
import manager.security.sensors.Sensor;

import java.io.IOException;
import java.util.*;

public class Room {
    private UUID id;
    private UUID floorId;
    private int windowsAmount;
    private int doorsAmount;
    private double area;
    private List<Sensor> sensors;

    protected Room(){
        id = null;
        floorId = null;
        windowsAmount = 0;
        doorsAmount = 0;
        area = 0.0;
        sensors = new ArrayList<>();
    }

    public Room(UUID floorId, int windowsAmount, int doorsAmount, double area) {
        id = UUID.randomUUID();
        this.floorId = floorId;
        sensors = new ArrayList<>();

        updateRoomParameters(windowsAmount, doorsAmount, area);
        Logger.getInstance().log(this.floorId, id, "Room was created", LogType.System);
    }

    public void updateRoomParameters(int windowsAmount, int doorsAmount, double area) {
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

        SensorCalculator.SensorResult result = null;
        try{
            SensorCalculator calculator = new SensorCalculator();
            result = calculator.calculateSensors(area, doorsAmount, windowsAmount);
        }catch(IOException e){
            Logger.getInstance().log(floorId, id, "Unable to read data from requirement file", LogType.System);
        }

        if(result != null) {
            for(int i = 0; i < result.getOpenSensors(); i++){
                sensorCreators.add(new OpenSensorCreator());
            }
            for(int i = 0; i < result.getMotionSensors(); i++){
                sensorCreators.add(new MotionSensorCreator());
            }
            for(int i = 0; i < result.getTemperatureSensors(); i++){
                sensorCreators.add(new TemperatureSensorCreator());
            }
        }


        // TODO: Delete section below after completing aforementioned task
        // start section
//        sensorCreators.add(new TemperatureSensorCreator());
//        for(int i = 0; i < doorsAmount+windowsAmount; i++) {
//            sensorCreators.add(new OpenSensorCreator());
//        }
//        for(int i = 0; i < area / 15 + 1; i++) {
//            sensorCreators.add(new MotionSensorCreator());
//        }
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
        return Collections.unmodifiableList(sensors);
    }

    protected void setId(UUID id) {this.id = id;}
    protected void setFloorId(UUID floorId) {this.floorId = floorId;}
    protected void setWindowsAmount(int windowsAmount) {this.windowsAmount = windowsAmount;}
    protected void setDoorsAmount(int doorsAmount) {this.doorsAmount = doorsAmount;}
    protected void setArea(double area) {this.area = area;}
    protected void setSensors(List<Sensor> sensors) {this.sensors = sensors;}
}
