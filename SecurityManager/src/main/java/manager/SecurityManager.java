package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SecurityManager {
    private List<Floor> floors = new ArrayList<>();
    // TODO: Add Journal private field

    public Floor addFloor() {
        var floor = new Floor();
        floors.add(floor);
        return floor;
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public void addFloorList(List<Floor> floorList) {
        floors.addAll(floorList);
    }

    //додала отримання кількості поверхів треба для юайки щоб весь час не тягати списки
    public int getFloorCount(){
        return floors.size();
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public boolean deleteFloor(UUID floorId) {
        return floors.removeIf(f -> f.getId().equals(floorId));
    }

    public boolean deleteRoom(UUID floorId, UUID roomId) {
        for(Floor floor : floors) {
            if(floor.getId().equals(floorId)) {
                return floor.deleteRoom(roomId);
            }
        }
        return false;
    }
}
