package com.udesc.chefs;

import com.udesc.logger.CookingLogger;

import javax.xml.ws.Endpoint;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChefSpawnerPublisher {

    private static final CookingLogger logger = new CookingLogger(ChefSpawner.class.getSimpleName());

    public static void main(String[] args) throws UnknownHostException {
        String host = InetAddress.getLocalHost().getHostName();
        String URL = "http://" + host + ":8080/chef-spawner";

        Endpoint.publish(URL, new ChefSpawner());
        logger.info("chef spawner published at: " + URL);
    }

}
