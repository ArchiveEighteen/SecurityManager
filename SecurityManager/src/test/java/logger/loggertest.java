package logger;

import java.util.List;

public class loggertest {
    public static void main(String[] args) {

        Logger logger = Logger.getInstance();
        logger.log("Added new floor with ID: " + "test1");

        LogReader logReader = LogReader.getInstance();

        List<String> logs = logReader.getLogs();
        for (String log : logs) {
            System.out.println(log);
        }
    }
}
