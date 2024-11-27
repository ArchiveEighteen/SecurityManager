package manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Floor {
    private UUID id;
    private List<Room> rooms;

    public Floor() {
        id = UUID.randomUUID();
        rooms = new ArrayList<>();
    }

    public Room addRoom(int windowsAmount, int doorsAmount, double area){
        Room room = new Room(id, windowsAmount, doorsAmount, area);
        rooms.add(room);
        return room;
    }
    public boolean deleteRoom(UUID roomId){
        return rooms.removeIf(r -> r.getId().equals(roomId));
    }

    public UUID getId(){
        return id;
    }
    public List<Room> getRooms(){
        return Collections.unmodifiableList(rooms);
    }

    // ID set is only possible during serialization
    protected void setId(UUID id) {
        this.id = id;
    }

    protected void addRoom(Room room){
        rooms.add(room);
    }
}
