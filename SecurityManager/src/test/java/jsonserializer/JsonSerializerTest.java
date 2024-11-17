package jsonserializer;

import manager.Floor;
import manager.Room;
import manager.SecurityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serializers.jsonserializer.JsonSerializer;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerTest extends JsonSerializer {

    SecurityManager securityManager = new SecurityManager();

    @BeforeEach
    void setUp() {
        var floors = new ArrayList<Floor>();

        floors.add(securityManager.addFloor());
        floors.add(securityManager.addFloor());

        var firstFloorRooms = new ArrayList<Room>();
        var secondFloorRooms = new ArrayList<Room>();

        firstFloorRooms.add(floors.get(0).addRoom(3, 2, 30));
        firstFloorRooms.add(floors.get(0).addRoom(2, 1, 15));

        secondFloorRooms.add(floors.get(1).addRoom(1, 1, 10));
        secondFloorRooms.add(floors.get(1).addRoom(0, 1, 5));
    }

    @Test
    void testReconstruct() {
        try {
            serialize(securityManager, "security_manager_state.json");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        SecurityManager sm = new SecurityManager();

        try {
            sm = deserialize("security_manager_state.json", SecurityManager.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertNotNull(sm);
        assertEquals(securityManager.getFloors().size(), sm.getFloors().size());

        var smFloorsInitial = securityManager.getFloors();
        var smFloorsResult = sm.getFloors();
        for(int i = 0; i < smFloorsInitial.size(); i++) {
            assertEquals(smFloorsInitial.get(i).getId(), smFloorsResult.get(i).getId());

            var smRoomsInitial = smFloorsInitial.get(i).getRooms();
            var smRoomsResult = smFloorsResult.get(i).getRooms();
            assertEquals(smRoomsInitial.size(), smRoomsResult.size());

            for(int j = 0; j < smRoomsInitial.size(); j++) {
                assertEquals(smRoomsInitial.get(j).getId(), smRoomsResult.get(j).getId());

                var smSensorsInitial = smRoomsInitial.get(j).getSensors();
                var smSensorsResult = smRoomsResult.get(j).getSensors();

                assertEquals(smSensorsInitial.size(), smSensorsResult.size());
                for(int k = 0; k < smSensorsInitial.size(); k++) {
                    assertEquals(smSensorsInitial.get(k).getId(), smSensorsResult.get(k).getId());
                }
            }
        }
    }
}