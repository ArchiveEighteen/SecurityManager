package logger;

import manager.Floor;
import manager.Room;
import manager.SecurityManager;
import manager.security.sensors.MotionSensor;
import manager.security.sensors.SensorType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggerTest {

    private final String logFile = "application.log";
    SecurityManager sm = new SecurityManager();
    MotionSensor motionSensor;
    Floor floor;
    Room room;

    @BeforeEach
    public void setUp() {
        floor = sm.addFloor();
        room = floor.addRoom(2,2, 25);
        var sensors = room.getSensors();

        for(var sensor : sensors) {
            if(sensor.getType() == SensorType.MotionSensor){
                motionSensor = (MotionSensor) sensor;
                break;
            }
        }
    }

    @AfterEach
    public void tearDown() {
        motionSensor = null;
        floor = null;
        room = null;
//        try(FileWriter writer = new FileWriter(logFile, false)){
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void LoggingTest() {
        motionSensor.detectMovement();

        assertEquals(floor.getId(), LogReader.getInstance().getLogs().getFirst().getFloorId());
        assertEquals(room.getId(), LogReader.getInstance().getLogs().getFirst().getRoomId());
        assertEquals("BreachDetected", LogReader.getInstance().getLogs().getFirst().getMessage());
        assertEquals(LogType.MotionSensor, LogReader.getInstance().getLogs().getFirst().getType());

    }
}
