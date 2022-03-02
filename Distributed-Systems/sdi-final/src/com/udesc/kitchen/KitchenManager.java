package com.udesc.kitchen;

import com.udesc.chefs.Chef;
import com.udesc.chefs.ChefInterface;
import com.udesc.chefs.ChefSpawnerInterface;
import com.udesc.logger.CookingLogger;
import com.udesc.utils.UrlBuilder;
import com.udesc.utils.Vec2;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

public class KitchenManager {

    private static String kitchenServiceURL;
    private static final CookingLogger logger = new CookingLogger(KitchenManager.class.getSimpleName());

    public static void main(String[] args) {
        try {
            ArrayList<String> input = parseInput(args);
            String inputMapName = input.get(0);
            ArrayList<String> hostsList = new ArrayList<>(input.subList(1, input.size()));

            FileReader input_map = new FileReader(inputMapName);
            BufferedReader reader = new BufferedReader(input_map);

            Kitchen kitchen = getKitchen(reader);

            String hostName = InetAddress.getLocalHost().getHostName();

            kitchenServiceURL = UrlBuilder.build(hostName, KitchenService.PORT, "kitchen");

            List<ChefSpawnerInterface> chefSpawners = new ArrayList<>();
            HashMap<Character, ChefInterface> chefs = new HashMap<>();

            int nChefsByHosts = kitchen.getChefs().size() / hostsList.size();
            int remainingChefs = kitchen.getChefs().size() % hostsList.size();

            Queue<Character> chefsToSpawn = new LinkedList<>(kitchen.getChefsIds());

            for (String host : hostsList) {
                ChefSpawnerInterface chefSpawner;
                try {
                    chefSpawner = getChefSpawner(host);
                } catch (Exception e) {
                    logger.error("Failed to establish connection to " + host + ": " + e);
                    continue;
                }

                chefSpawners.add(chefSpawner);

                for (int i = 0; i < nChefsByHosts; i++) {
                    Character chefId = chefSpawner.spawnChef(chefsToSpawn.remove());

                    ChefInterface chef = getChef(host, chefId);

                    chefs.put(chefId, chef);
                }
                if (remainingChefs > 0) {
                    Character chefId = chefSpawner.spawnChef(chefsToSpawn.remove());

                    ChefInterface chef = getChef(host, chefId);

                    chefs.put(chefId, chef);
                    remainingChefs--;
                }
            }

            KitchenServiceInterface kitchenService = startKitchenService(kitchen);

            chefs.forEach((chefId, chef) -> {
                chef.start(kitchen.getMap(), chefId, kitchenServiceURL);
            });

            logger.info("Entering in the main loop");
            while (!kitchenService.isFinished()) {
                Thread.sleep(100);
            }

            chefSpawners.forEach(chefSpawner -> {
                try {
                    chefSpawner.kill();
                } catch (WebServiceException e) {
                    logger.info("chef spawner is down");
                }
            });

            System.exit(0);
        } catch (FileNotFoundException e) {
            logger.error("No file with path " + args[0] + " could be find");
        } catch (IOException | NumberFormatException e) {
            logger.error("Got an error while opening the file");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static KitchenServiceInterface startKitchenService(Kitchen kitchen) throws InterruptedException, MalformedURLException {
        Endpoint.publish(kitchenServiceURL, new KitchenService());

        URL url = new URL(kitchenServiceURL);
        QName qname = new QName("http://kitchen.udesc.com/", "KitchenServiceService");
        Service chefWs = Service.create(url, qname);
        KitchenServiceInterface kitchenServiceInterface = chefWs.getPort(KitchenServiceInterface.class);

        kitchenServiceInterface.start(kitchen);

        return kitchenServiceInterface;
    }

    private static ChefInterface getChef(String host, Character chefId) throws MalformedURLException {
        URL chefUrl = new URL(UrlBuilder.build(host, Chef.PORT, chefId));
        QName chefQname = new QName("http://chefs.udesc.com/", "ChefService");
        Service chefWs = Service.create(chefUrl, chefQname);

        return chefWs.getPort(ChefInterface.class);
    }

    private static ChefSpawnerInterface getChefSpawner(String host) throws MalformedURLException {
        URL url = new URL(UrlBuilder.build(host, Chef.PORT, "chef-spawner"));
        QName qname = new QName("http://chefs.udesc.com/", "ChefSpawnerService");
        Service ws = Service.create(url, qname);

        return ws.getPort(ChefSpawnerInterface.class);
    }

    private static Kitchen getKitchen(BufferedReader reader) throws IOException {
        String[] inputSize = reader.readLine().split(" ");
        int n = Integer.parseInt(inputSize[0]);
        int m = Integer.parseInt(inputSize[1]);

        char[][] map = new char[n][m];
        HashMap<Character, Vec2> chefs = new HashMap<>();
        HashMap<Vec2, Table> tables = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String line = reader.readLine();
            map[i] = line.toCharArray();

            for (int j = 0; j < m && j < map[i].length; j++) {
                char c = map[i][j];

                Vec2 position = new Vec2(i, j);
                if ('A' <= c && c <= 'Z') { // It's a chef
                    chefs.put(c, position);
                    logger.debug("Chef found: " + c + " at " + position);
                } else if (c == KitchenMap.ENTRANCE) { // it's an entrance
                    int plates = map[i][j+1] - '0';
                    tables.put(position, Table.entrance(position, plates));
                    logger.debug("Entrance found at " + position + " with " + plates + " plates");
                } else if (c == KitchenMap.EXIT) { // it's an exit
                    tables.put(position, Table.exit(position));
                    logger.debug("Exit found at " + position);
                } else if (c == KitchenMap.EMPTY_TABLE) { // it's a table
                    tables.put(position, new Table(position));
                    logger.debug("Table found at " + position);
                }
            }
        }

        KitchenMap kitchenMap = new KitchenMap(map);
        Kitchen kitchen = new Kitchen(kitchenMap, chefs, tables);
        computeTablePriority(kitchen);
        return kitchen;
    }

    private static void computeTablePriority(Kitchen kitchen) {
        KitchenMap map = kitchen.getMap();
        int[][] priority_map = new int[map.getN()][map.getM()];
        Queue<Vec2> fila = new LinkedList<>();
        for (Table exit: kitchen.getExits()) {
            Vec2 position = exit.getPosition();
            exit.setPriority(1);
            priority_map[position.getX()][position.getY()] = 1;
            fila.add(position);
        }

        while (!fila.isEmpty()) {
            Vec2 u = fila.remove();

            for (Vec2 d : Vec2.ADJACENT_DIRECTION) {
                Vec2 v = Vec2.sum(u, d);
                if (map.isInside(v)) {
                    if (map.isWall(v)) continue;
                    if (priority_map[v.getX()][v.getY()] != 0) continue;
                    priority_map[v.getX()][v.getY()] = priority_map[u.getX()][u.getY()];
                    if (map.isTable(v)) {
                        priority_map[v.getX()][v.getY()]++;
                        kitchen.getTables().get(v).setPriority(priority_map[v.getX()][v.getY()]);
                    }
                    fila.add(v);
                }
            }
        }

//        StringBuilder sb = new StringBuilder();
//        sb.append("Priority map:\n");
//        for (int i = 0; i < map.getN(); i++) {
//            for (int j = 0; j < map.getM(); j++) {
//                sb.append(priority_map[i][j]);
//            }
//            sb.append('\n');
//        }
//        logger.info(sb.toString());
    }

    private static ArrayList<String> parseInput(String[] args) throws UnknownHostException {
        if (args[0] == null || args[0].trim().isEmpty()) {
            System.out.println("Error while reading the input!");
            System.out.println("You need to specify the map name as the first param");
            System.out.println("It's ok to not specify the hosts list, but it will run all the chefs in this host");
            System.exit(1);
        }
        ArrayList<String> input = new ArrayList<>();
        input.add(args[0]);
        // if the hostsList isn't informed, add localHost
        if (args[1] == null || args[1].trim().isEmpty()){
            input.add(InetAddress.getLocalHost().getHostName());
            return input;
        }
        input.addAll(Arrays.asList(args).subList(1, args.length));
        return input;
    }
}
