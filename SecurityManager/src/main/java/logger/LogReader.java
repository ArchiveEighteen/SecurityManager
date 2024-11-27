package logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LogReader {
    private static LogReader instance;
    private static final String LOG_FILE = "application.log";

    private LogReader() {}

    public static synchronized LogReader getInstance() {
        if (instance == null) {
            instance = new LogReader();
        }
        return instance;
    }

    //Зчитує логи з фіксованого файлу і повертає їх у вигляді списку рядків.

    public List<Log> getLogs() {
        List<Log> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(ParseLogString(line));
            }
        } catch (IOException e) {
            //logs.add("Error reading logs: " + e.getMessage());
        }
        return logs;
    }

    private Log ParseLogString(String log) {
        String[] parts = log.split("/");

        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid log format");
        }

        // Parse LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.parse(parts[0], formatter);

        // Parse UUIDs
        UUID firstUuid = UUID.fromString(parts[1]);
        UUID secondUuid = UUID.fromString(parts[2]);

        // Parse String
        String logMessage = parts[3];

        // Parse Enum (LogType)
        LogType logType;
        try {
            logType = LogType.valueOf(parts[4]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid LogType: " + parts[4]);
        }

        // Return ParsedLog
        return new Log(timestamp, firstUuid, secondUuid, logMessage, logType);
    }

}
