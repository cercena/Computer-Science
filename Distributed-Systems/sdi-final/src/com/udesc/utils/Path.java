package com.udesc.utils;

import java.util.ArrayDeque;
import java.util.Deque;

public class Path {

    private final Deque<Vec2> points = new ArrayDeque<>();

    public int size() { return this.points.size(); }

    public boolean isEmpty() { return this.points.isEmpty(); }

    public Vec2 getTarget() { return this.points.getLast(); }

    public boolean onlyTarget() { return this.size() == 1; }

    public void add(Vec2 v) {
        points.add(v);
    }

    public void addBefore(Vec2 v) {
        points.addFirst(v);
    }

    public Vec2 next() {
        return points.remove();
    }

    public Vec2 peek() {
        return points.getFirst();
    }

    @Override
    public String toString() {
        return "Path{"+ points +'}';
    }
}
