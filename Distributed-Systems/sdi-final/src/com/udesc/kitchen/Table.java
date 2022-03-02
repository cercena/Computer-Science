package com.udesc.kitchen;

import com.udesc.utils.Vec2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Table {
    private final Vec2 position;
    private boolean occupied = false;
    private boolean entrance = false;
    private boolean exit = false;
    private int priority = -1;
    private int plates = 0;

    public Table() {
        this.position = new Vec2(-1, -1);
    }

    public Table(Vec2 position) {
        this.position = position;
    }

    static public Table entrance(Vec2 position, int plates) {
        Table table = new Table(position);
        table.plates = plates;
        table.occupied = true;
        table.entrance = true;
        return table;
    }

    static public Table exit(Vec2 position) {
        Table table = new Table(position);
        table.exit = true;
        return table;
    }

    public Vec2 getPosition() {
        return position;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isEntrance() {
        return entrance;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isEmpty() {
        return plates == 0;
    }

    public boolean hasPlate() {
        return plates > 0;
    }

    public void addPlate() {
        plates++;
    }

    public void removePlate() {
        plates--;
    }

    public int getPlates() {
        return plates;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
