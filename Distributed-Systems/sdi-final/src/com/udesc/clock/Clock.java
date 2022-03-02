package com.udesc.clock;

public class Clock {

    private int time = 0;

    public void updateTime() {
        this.time++;
    }

    public int getTime() {
        return time;
    }

}
