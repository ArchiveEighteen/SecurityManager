package logger;

import java.time.LocalDateTime;
import java.util.UUID;

public class Log {
    private LocalDateTime time;
    private UUID floorId;
    private UUID roomId;
    private String message;
    private LogType type;

    public Log(LocalDateTime time, UUID floorId, UUID roomId, String message, LogType type) {
        this.time = time;
        this.floorId = floorId;
        this.roomId = roomId;
        this.message = message;
        this.type = type;
    }

    public Log(){
        this.time = null;
        this.floorId = null;
        this.roomId = null;
        this.message = null;
        this.type = null;
    }


    public LocalDateTime getTime() {
        return time;
    }

    public UUID getFloorId() {
        return floorId;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public String getMessage() {
        return message;
    }

    public LogType getType() {
        return type;
    }


    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void getFloorId( UUID floorId ) {
        this.floorId = floorId;
    }

    public void getRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public void getMessage(String message) {
        this.message = message;
    }

    public void getType(LogType type) {
        this.type = type;
    }
}
