package com.udesc.utils;

import java.util.Objects;

public class Vec2 {
    public static final Vec2[] ADJACENT_DIRECTION = new Vec2[]{new Vec2(-1, 0), new Vec2(1, 0), new Vec2(0, -1), new Vec2(0, 1)};

    private int x;
    private int y;

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Vec2 sum(Vec2 a, Vec2 b) {
        return new Vec2(a.getX() + b.getX(), a.getY() + b.getY());
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2 vec2 = (Vec2) o;
        return x == vec2.x && y == vec2.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
