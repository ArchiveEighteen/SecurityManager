package serializers.jsonserializer.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import manager.SecurityManager;
import manager.security.sensors.*;
import serializers.serializationclasses.SerializationFloor;
import serializers.serializationclasses.SerializationRoom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Flow;

public class SecurityManagerAdapter extends TypeAdapter<SecurityManager> {
    @Override
    public void write(JsonWriter jsonWriter, SecurityManager securityManager) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("floors");
        jsonWriter.beginArray();
        for(var floor : securityManager.getFloors()){
            jsonWriter.beginObject();

            jsonWriter.name("id").value(floor.getId().toString());

            jsonWriter.name("rooms");
            jsonWriter.beginArray();
            for(var room : floor.getRooms()){
                jsonWriter.beginObject();

                jsonWriter.name("id").value(room.getId().toString());
                jsonWriter.name("windowsAmount").value(room.getWindowsAmount());
                jsonWriter.name("doorsAmount").value(room.getDoorsAmount());
                jsonWriter.name("area").value(room.getArea());

                jsonWriter.name("sensors");
                jsonWriter.beginArray();
                for(var sensor : room.getSensors()){
                    jsonWriter.beginObject();

                    jsonWriter.name("id").value(sensor.getId().toString());
                    jsonWriter.name("status").value(sensor.getStatus().get());
                    if(sensor instanceof TemperatureSensor){
                        jsonWriter.name("type").value("temperature");
                        jsonWriter.name("currentTemperature").value(((TemperatureSensor) sensor).getCurrentTemperature());
                        jsonWriter.name("maxTemperature").value(((TemperatureSensor) sensor).getMaxTemperature());
                        jsonWriter.name("minTemperature").value(((TemperatureSensor) sensor).getMinTemperature());
                    } else if (sensor instanceof OpenSensor) {
                        jsonWriter.name("type").value("open");
                    } else if (sensor instanceof MotionSensor) {
                        jsonWriter.name("type").value("motion");
                    }

                    jsonWriter.endObject();
                }
                jsonWriter.endArray();
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
            jsonWriter.endObject();
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
    }

    @Override
    public SecurityManager read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();

        var securityManager = new SecurityManager();

        // One security manager object possible, thus no loop
        if (jsonReader.nextName().equals("floors")) {
            jsonReader.beginArray();

            // Each floor deserialization
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                SerializationFloor floor = new SerializationFloor();

                // Read floor fields
                while (jsonReader.hasNext()) {
                    String floorName = jsonReader.nextName();

                    if(floorName.equals("id")){
                        floor.setId(UUID.fromString(jsonReader.nextString()));
                    }
                    else if (floorName.equals("rooms")) {
                        jsonReader.beginArray();

                        // Rooms deserialization
                        while (jsonReader.hasNext()) {
                            jsonReader.beginObject();
                            SerializationRoom room = new SerializationRoom();

                            // Read room fields
                            while (jsonReader.hasNext()) {
                                room.setFloorId(floor.getId());

                                String roomName = jsonReader.nextName();
                                
                                if(roomName.equals("id")){
                                    room.setId(UUID.fromString(jsonReader.nextString()));
                                } else if (roomName.equals("windowsAmount")) {
                                    room.setWindowsAmount(jsonReader.nextInt());
                                } else if (roomName.equals("doorsAmount")) {
                                    room.setDoorsAmount(jsonReader.nextInt());
                                } else if (roomName.equals("area")) {
                                    room.setArea(jsonReader.nextDouble());
                                } else if (roomName.equals("sensors")) {
                                    jsonReader.beginArray();
                                    List<Sensor> sensors = new ArrayList<>();

                                    // Sensors deserialization
                                    while (jsonReader.hasNext()) {
                                        jsonReader.beginObject();
                                        UUID id = null;
                                        boolean status = false;
                                        String type = "";
                                        double currentTemperature = 0.0;
                                        double maxTemperature = 0.0;
                                        double minTemperature = 0.0;
                                        Sensor sensor = null;

                                        // Read sensor fields
                                        while(jsonReader.hasNext()){
                                            String sensorName = jsonReader.nextName();
                                            if(sensorName.equals("id")) {
                                                id = UUID.fromString(jsonReader.nextString());
                                            } else if(sensorName.equals("status")){
                                                status = jsonReader.nextBoolean();
                                            } else if(sensorName.equals("type")) {
                                                type = jsonReader.nextString();
                                            } else if(sensorName.equals("currentTemperature")) {
                                                currentTemperature = jsonReader.nextDouble();
                                            } else if(sensorName.equals("maxTemperature")) {
                                                maxTemperature = jsonReader.nextDouble();
                                            } else if(sensorName.equals("minTemperature")) {
                                                minTemperature = jsonReader.nextDouble();
                                            }

                                            // Recreate specific sensors
                                            if(type.equals("motion")){
                                                sensor = new MotionSensor(id, floor.getId(), room.getId(), status);
                                            } else if(type.equals("open")){
                                                sensor = new OpenSensor(id, floor.getId(), room.getId(), status);
                                            } else if(type.equals("temperature")){
                                                sensor = new TemperatureSensor(id, floor.getId(), room.getId(), status,
                                                        currentTemperature, minTemperature, maxTemperature);
                                            }
                                        }
                                        jsonReader.endObject();
                                        sensors.add(sensor);
                                    }
                                    jsonReader.endArray();
                                    room.setSensors(sensors);
                                }
                            }
                            jsonReader.endObject();
                            floor.addRoom(room);
                        }
                        jsonReader.endArray();
                    }
                }
                jsonReader.endObject();
                securityManager.addFloor(floor);
            }
            jsonReader.endArray();
        }

        jsonReader.endObject();
        return securityManager;
    }
}
