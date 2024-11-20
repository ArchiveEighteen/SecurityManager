package logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getLogs() {
        List<String> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException e) {
            logs.add("Error reading logs: " + e.getMessage());
        }
        return logs;
    }

}
