package com.pawns;

import com.level.Field;

import java.util.ArrayList;

public abstract class Pawn {

    protected String name;

    private int speed;
    // private int mvRange;
    private int maxHealth;

    private Attack attack1;
    private Attack attack2;

    private ArrayList<PawnSegment> segments;

    public Pawn() {

    }

    public void createSegment(Field field) {
        PawnSegment newSeg = new PawnSegment(this, true);
        field.setSegment(newSeg);
    }

    public int getSpeed() {
        return speed;
    }

    public void move() {

    }

    public String getName() {
        return name;
    }

    abstract void attack();
}
