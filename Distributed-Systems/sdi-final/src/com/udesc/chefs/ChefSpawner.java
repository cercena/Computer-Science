package com.udesc.chefs;

import com.udesc.logger.CookingLogger;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.net.InetAddress;
import java.net.UnknownHostException;

@WebService(endpointInterface = "com.udesc.chefs.ChefSpawnerInterface")
public class ChefSpawner implements ChefSpawnerInterface {

    private final CookingLogger logger = new CookingLogger(ChefSpawner.class.getSimpleName());

    @Override
    public Character spawnChef(Character chefId) throws UnknownHostException {
        String url = getUrl(chefId);

        logger.info("chef spawned at: " + url);
        Endpoint.publish(url, new Chef());

        return chefId;
    }

    @Override
    public void kill() {
        logger.info("chef-spawner killed");
        System.exit(0);
    }

    private String getUrl(Character chefId) throws UnknownHostException {
        String processPort = "8080";
        String host = InetAddress.getLocalHost().getHostName();
        String baseUrl = "http://" + host + ":" + processPort;

        return baseUrl + '/' + chefId;
    }

}
