package com.tongwii.logging;

import ch.qos.logback.classic.Logger;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * View Model object for storing a Logback logger.
 */
@Getter@Setter@ToString
public class LoggerVM {

    private String name;

    private String level;

    public LoggerVM(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    public LoggerVM() {
        // Empty public constructor used by Jackson.
    }
}
