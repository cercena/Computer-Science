package com.udesc.logger;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CookingLogger {

    private final String name;
    private boolean log = Boolean.parseBoolean(System.getenv("JAVA_LOG"));

    public CookingLogger(String name) {
        this.name = name;
    }

    public void info(String logThis) {
        if (log)
        System.out.println(this.prefix() + " INFO: " + logThis);
    }

    public void debug(String logThis) {
        if (log)
        System.out.println(this.prefix() + " DEBUG: " + logThis);
    }

    public void error(String logThis) {
        if (log)
        System.err.println(this.prefix() + " ERROR: " + logThis);
    }

    private String prefix()  {
        try {
            return "[" + this.name + "@" + InetAddress.getLocalHost().getHostName() + "]";
        } catch (UnknownHostException e) {
            return "[" + this.name + "]";
        }
    }

}
