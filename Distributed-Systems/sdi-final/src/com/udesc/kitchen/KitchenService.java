package com.udesc.kitchen;

import com.udesc.clock.Clock;
import com.udesc.logger.CookingLogger;
import com.udesc.utils.Vec2;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface = "com.udesc.kitchen.KitchenServiceInterface")
public class KitchenService extends Clock implements KitchenServiceInterface {

    public static String PORT = "9000";
    private Kitchen kitchen;
    private static final CookingLogger logger = new CookingLogger(KitchenManager.class.getSimpleName());

    @WebMethod
    public void start(@WebParam(name = "kitchen") Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    @WebMethod
    public boolean walk(
            @WebParam(name = "position") Vec2 position,
            @WebParam(name = "chefId") Character chefId,
            @WebParam(name = "full") boolean full
    ) {
        if (kitchen.getMap().isWalkable(position)){
            kitchen.getMap().set(kitchen.getChefs().get(chefId), ' ');
            kitchen.getChefs().put(chefId, position);
            kitchen.getMap().set(position, chefId);
            this.updateTime();
            logger.info(kitchen.getMap().toString());
            System.out.println(
                    this.getTime()+";"
                    +chefId+";"
                    +kitchen.getChefs().get(chefId).getX()+";"
                    +kitchen.getChefs().get(chefId).getX()+";"
                    +position.getX()+";"
                    +position.getY()+";"
                    +"S;"
                    +(full ? '1' : '0')
            );
            return true;
        }
        return false;
    }

    @WebMethod
    public boolean get(
            @WebParam(name = "position") Vec2 position,
            @WebParam(name = "chefId") Character chefId,
            @WebParam(name = "full") boolean full
    ) {
        if (!kitchen.getMap().isTable(position)) return false;

        Table table = kitchen.getTables().get(position);
        if (table.hasPlate()){
            table.removePlate();
            if (!table.isEntrance()) kitchen.getMap().set(position, '$');
            else {
                char plates = (char) (table.getPlates() + '0');
                Vec2 numberPosition = new Vec2(position.getX(), position.getY()+1);
                this.kitchen.getMap().set(numberPosition, plates);
            }
            this.updateTime();
            logger.info(kitchen.getMap().toString());
            System.out.println(
                    this.getTime()+";"
                    +chefId+";"
                    +kitchen.getChefs().get(chefId).getX()+";"
                    +kitchen.getChefs().get(chefId).getX()+";"
                    +position.getX()+";"
                    +position.getY()+";"
                    +"G;"
                    +(full ? '1' : '0')
            );
            return true;
        }
        return false;
    }

    @WebMethod
    public boolean put(
            @WebParam(name = "position") Vec2 position,
            @WebParam(name = "chefId") Character chefId,
            @WebParam(name = "full") boolean full
    ) {
        if (!kitchen.getMap().isTable(position)) return false;

        Table table = kitchen.getTables().get(position);
        if (!table.hasPlate() || table.isExit()){
            table.addPlate();
            if (!table.isExit()) kitchen.getMap().set(position, '&');
            logger.info(kitchen.getMap().toString());
            this.updateTime();
            System.out.println(
                    this.getTime()+";"
                    +chefId+";"
                    +kitchen.getChefs().get(chefId).getX()+";"
                    +kitchen.getChefs().get(chefId).getX()+";"
                    +position.getX()+";"
                    +position.getY()+";"
                    +"P;"
                    +(full ? '1' : '0')
            );

            return true;
        }
        return false;
    }

    @Override
    public Integer getTablePriority(Vec2 position) {
        return kitchen.getTables().get(position).getPriority();
    }

    @Override
    public boolean isFinished() {
        int finished_plates = 0;
        for (Table table : this.kitchen.getTables().values()) {
            if (table.isExit()) finished_plates += table.getPlates();
        }
        return finished_plates == kitchen.getPlates();
    }

    @Override
    public boolean hasPlate(Vec2 position) {
        return kitchen.getTables().get(position).hasPlate();
    }

    @Override
    public boolean isExit(Vec2 position) {
        return kitchen.getTables().get(position).isExit();
    }

    @Override
    public boolean isEntrance(Vec2 position) {
        return kitchen.getTables().get(position).isEntrance();
    }

}
