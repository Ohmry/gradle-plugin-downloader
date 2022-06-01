package io.ohmry.logger;

public class Logger {
    private static final String PRINT_FORMAT = "%s[%6s]%s %s";
    private static void print (LogType logType, String message) {
        System.out.printf((PRINT_FORMAT) + "%n", logType.getColor(), logType.getName(), LogColor.RESET.getValue(), message);
    }
    public static void info (String message) {
        print(LogType.INFO, message);
    }
    public static void debug (String message) {
        print(LogType.DEBUG, message);
    }
    public static void warn (String message) {
        print(LogType.WARN, message);
    }
    public static void error (String message) {
        print(LogType.ERROR, message);
    }

}
