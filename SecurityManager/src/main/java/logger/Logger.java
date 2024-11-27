package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Logger {
    private static Logger instance;
    private final String logFile = "application.log";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(UUID floorId, UUID roomId, String message, LogType logType) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logMessage = timestamp + "/" + floorId + "/" + roomId + "/" + message + "/" + logType.name();

        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(logMessage + "\n");
        } catch (IOException e) {
            System.err.println("Error writing log: " + e.getMessage());
        }
    }
}
