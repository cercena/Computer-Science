package com.udesc.chefs;

import com.udesc.kitchen.KitchenMap;
import com.udesc.kitchen.KitchenServiceInterface;
import com.udesc.logger.CookingLogger;
import com.udesc.utils.Path;
import com.udesc.utils.Vec2;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@WebService(endpointInterface = "com.udesc.chefs.ChefInterface")
public class Chef implements ChefInterface {

    public static final String PORT = "8080";

    private final CookingLogger logger = new CookingLogger(Chef.class.getSimpleName());
    private KitchenServiceInterface kitchenService;

    private Character chefId;
    private KitchenMap kitchenMap;
    private Vec2 position;
    private List<Vec2> exits;
    private List<Vec2> entrances;
    private Path currentPath;
    private boolean handsFree;

    @Override
    public void start(
            @WebParam(name = "kitchenMap") KitchenMap kitchenMap,
            @WebParam(name = "chefId") Character chefId,
            @WebParam(name = "kitchenServiceURL") String kitchenServiceURL
    ) {
        try {
            this.chefId = chefId;
            this.kitchenMap = kitchenMap;
            this.kitchenService = getKitchenService(kitchenServiceURL);
            this.handsFree = true;
            reconMap();

            logger.debug("CHEF " + chefId + " at " + position + ":\n"
                    + kitchenMap.toString()
                    + "exits: " + exits + '\n'
                    + "entrances: " + entrances + '\n'
                    + "path_to_exit: " + findPath(exits.get(0)) + '\n'
                    + "path_to_entrance: " + findPath(entrances.get(0)) + '\n');

            Runnable walkThread = () -> {
                walk();
            };

            new Thread(walkThread).start();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void walk() {
        while (true) {
            logger.debug(chefId + " handsFree: " + this.handsFree);
            if (this.currentPath == null || this.currentPath.isEmpty()) {
//                logger.debug(this.chefId + " find path");
                currentPath = this.findCurrentPath();
            }
            if (currentPath == null) continue;
            if (!this.currentPath.onlyTarget()) {
                logger.debug(this.chefId + " tried to walk");
                Vec2 nextPosition = this.currentPath.next();
                if (this.kitchenService.walk(nextPosition, this.chefId, this.handsFree)) {
                    logger.debug(this.chefId + " tried to walk");
                    this.position = nextPosition;
                } else {
                    this.kitchenMap.set(nextPosition, '|');
                    this.currentPath = this.findCurrentPath();
                    this.kitchenMap.set(nextPosition, ' ');
                }
            } else if (this.currentPath.onlyTarget()) {

                Vec2 target = this.currentPath.peek();
                if (this.handsFree) {
                    logger.debug(this.chefId + " tried to get");
                    if (this.kitchenService.get(target, chefId, this.handsFree)) {
                        this.currentPath.next();
                        this.handsFree = false;
                    }
                }
                else {
                    logger.debug(this.chefId + " tried to put");
                    if (this.kitchenService.put(target, chefId, this.handsFree)) {
                        this.currentPath.next();
                        this.handsFree = true;
                    }
                }
            }
        }
    }

    private Path findCurrentPath() {
        if (this.handsFree) {
            for (Vec2 entrance : entrances) {
                if (kitchenService.hasPlate(entrance)) return findPath(entrance);
            }
//            return findPath(entrances.get(0));
        } else {
            for (Vec2 exit : exits) {
                if (kitchenService.isExit(exit) || !kitchenService.hasPlate(exit))
                    return findPath(exit);
            }
        }
        return null;
    }

    private KitchenServiceInterface getKitchenService(String kitchenServiceURL) throws MalformedURLException {
        URL url = new URL(kitchenServiceURL);
        QName qname = new QName("http://kitchen.udesc.com/", "KitchenServiceService");
        Service ws = Service.create(url, qname);

        return ws.getPort(KitchenServiceInterface.class);
    }

    private void reconMap() {
        findMyPosition();
        Set<Vec2> visited = new HashSet<>();
        HashMap<Vec2, Integer> tablesPriority = new HashMap<>();
        Queue<Vec2> fila = new LinkedList<>();
        fila.add(position);
        while (!fila.isEmpty()) {
            Vec2 u = fila.remove();

            for (Vec2 d : Vec2.ADJACENT_DIRECTION) {
                Vec2 v = Vec2.sum(u, d);
                if (kitchenMap.isInside(v) && !visited.contains(v)) {
                    if (kitchenMap.isWall(v)) continue;
                    if (kitchenMap.isTable(v)) {
                        tablesPriority.put(v, kitchenService.getTablePriority(v));
                        continue;
                    }
                    visited.add(v);
                    fila.add(v);
                }
            }
        }

        int exitPriority = Collections.min(tablesPriority.values());
        exits = tablesPriority.entrySet().stream().filter(v -> v.getValue() == exitPriority).map(Map.Entry::getKey).collect(Collectors.toList());
        entrances = tablesPriority.entrySet().stream().filter(v -> v.getValue() != exitPriority).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public Path findPath(Vec2 target) {
        Set<Vec2> visited = new HashSet<>();
        HashMap<Vec2, Vec2> before = new HashMap<>();
        Queue<Vec2> fila = new LinkedList<>();
        fila.add(position);
        while (!fila.isEmpty()) {
            Vec2 u = fila.remove();
            if (u.equals(target)) break;

            for (Vec2 d : Vec2.ADJACENT_DIRECTION) {
                Vec2 v = Vec2.sum(u, d);
                if (kitchenMap.isInside(v) && !visited.contains(v)) {
                    if (kitchenMap.isWall(v)) continue;
                    visited.add(v);
                    before.put(v, u);
                    fila.add(v);
                }
            }
        }

        Path path = new Path();
        for (Vec2 i = target; i != position; i = before.get(i)) path.addBefore(i);
        return path;
    }

    private void findMyPosition() {
        for (int i = 0; i < kitchenMap.getN(); i++) {
            for (int j = 0; j < kitchenMap.getM(); j++) {
                if (kitchenMap.get(i, j) == chefId) {
                    position = new Vec2(i, j);
                }
            }
        }
    }

    @Override
    public Character getChefId() {
        return this.chefId;
    }

}
