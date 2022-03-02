package com.udesc.kitchen;

import com.udesc.utils.Vec2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class KitchenMap {

    public final static Set<Character> WALLS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList('|', '_', '#')));
    public final static Set<Character> TABLES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList('$', '+', '-', '&')));
    public final static char EMPTY_TABLE = '$';
    public final static char ENTRANCE = '+';
    public final static char EXIT = '-';

    private int n = 0;
    private int m = 0;
    private char[][] map;

    public KitchenMap(char[][] map) {
        this.map = map;
        this.n = map.length;
        this.m = map[0].length;
    }

    public KitchenMap(char[][] map, int n, int m) {
        this.map = map;
        this.m = m;
        this.n = n;
    }

    public KitchenMap() {
    }

    public char[][] getMap() {
        return map;
    }

    public char get(int x, int y) {
        return map[x][y];
    }

    public char get(Vec2 vec) {
        return map[vec.getX()][vec.getY()];
    }

    public void set(int x, int y, char c) {
        map[x][y] = c;
    }

    public void set(Vec2 vec, char c) {
        map[vec.getX()][vec.getY()] = c;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public boolean isInside(Vec2 v) {
        return 0 <= v.getX() && v.getX() < n && 0 <= v.getY() && v.getY() < m;
    }

    public boolean isWalkable(Vec2 v) { return (this.isInside(v) && this.get(v) == ' '); }

    public boolean isWall(Vec2 v) {
        return WALLS.contains(get(v));
    }

    public boolean isTable(Vec2 v) {
        return TABLES.contains(get(v));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (m/2) - 4; i++) sb.append(' ');
        sb.append("Kitchen");
        for (int i = 0; i < (m/2) - 4; i++) sb.append(' ');
        sb.append("\n");

        for (char[] l : this.map) {
            for (char c : l) sb.append(c);
            sb.append('\n');
        }

        return sb.toString();
    }
}
