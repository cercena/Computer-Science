package com.udesc.kitchen;

import com.udesc.utils.Vec2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Kitchen {

    private KitchenMap map;
    private HashMap<Character, Vec2> chefs;
    private HashMap<Vec2, Table> tables;
    private List<Table> entrances;
    private List<Table> exits;
    private int plates;

    public Kitchen(KitchenMap map, HashMap<Character, Vec2> chefs, HashMap<Vec2, Table> tables) {
        this.map = map;
        this.chefs = chefs;
        this.tables = tables;
        entrances = tables.values().stream().filter(Table::isEntrance).collect(Collectors.toList());
        exits = tables.values().stream().filter(Table::isExit).collect(Collectors.toList());
        plates = entrances.stream().map(Table::getPlates).reduce(0, Integer::sum);
    }

    public Kitchen() { }

    public KitchenMap getMap() {
        return map;
    }

    public HashMap<Character, Vec2> getChefs() {
        return chefs;
    }

    public Set<Character> getChefsIds() {
        return chefs.keySet();
    }

    public HashMap<Vec2, Table> getTables() {
        return tables;
    }

    public List<Table> getEntrances() {
        return entrances;
    }

    public List<Table> getExits() {
        return exits;
    }

    public int getPlates() {
        return plates;
    }
}
