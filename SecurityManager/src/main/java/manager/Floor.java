package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Floor {
    private UUID id = UUID.randomUUID();
    private List<Room> rooms = new ArrayList<>();

    public Room addRoom(int windowsAmount, int doorsAmount, double area){
        var room = new Room(id, windowsAmount, doorsAmount, area);
        rooms.add(room);
        return room;
    }
    public boolean deleteRoom(UUID roomId){
        return rooms.removeIf(r -> r.getId().equals(id));
    }

    public UUID getId(){
        return id;
    }
    public List<Room> getRooms(){
        return rooms;
    }

}
