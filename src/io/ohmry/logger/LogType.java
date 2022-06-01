package io.ohmry.logger;

public enum LogType {
    INFO("INFO", LogColor.BLUE_BOLD),
    DEBUG("DEBUG", LogColor.GREEN_BOLD),
    WARN("WARN", LogColor.YELLOW_BOLD),
    ERROR("ERROR", LogColor.RED_BOLD)

    ;
    private final String name;
    private final LogColor color;

    LogType (String name, LogColor color) {
        this.name = name;
        this.color = color;
    }
    public String getName () {
        return this.name;
    }
    public String getColor () {
        return this.color.getValue();
    }
}
