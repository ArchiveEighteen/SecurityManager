package serializers.serializationclasses;

import com.sun.source.tree.UsesTree;
import manager.Floor;
import manager.Room;

import java.util.UUID;

public class SerializationFloor extends Floor {

    public SerializationFloor() {
        super();
    }

    @Override
    public void setId(UUID id) {
        super.setId(id);
    }

    @Override
    public void addRoom(Room room){
        super.addRoom(room);
    }
}
