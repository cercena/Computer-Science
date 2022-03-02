package com.udesc.utils;

import java.net.URL;

public abstract class UrlBuilder {

    public static String build(String host, String port, String endpoint) {
        return "http://" + host + ":" + port + "/" + endpoint;
    }

    public static String build(String host, String port, Character endpoint) {
        return "http://" + host + ":" + port + "/" + endpoint;
    }

}
