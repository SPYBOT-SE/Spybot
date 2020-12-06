package com.pawns;

import com.level.Field;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Pawn {

    protected String name;

    protected byte speed;
    protected byte leftSteps;
    // private int mvRange;
    protected byte maxSize;

    private Attack attack1;
    private Attack attack2;


    private LinkedList<PawnSegment> segments = new LinkedList<>();

    public Pawn() {

    }

    public void createSegment(Field field) {
        PawnSegment newSeg = new PawnSegment(this, true, field);
        segments.add(newSeg);
        field.setSegment(newSeg);
    }

    public boolean move(Field field) {
        if(field.getSegment() != null) {
            return false;
        }

        createSegment(field);

        for(PawnSegment segment: segments) {
            segment.setNotHead();
        }

        while(segments.size() > maxSize) {
            Field segField = segments.getLast().getField();
            segField.setSegment(null);
            segments.removeLast();
        }


        return true;

    }

    abstract void attack();

// ----- Getter & Setter -----

    public byte getSpeed() {
        return speed;
    }

    public LinkedList<PawnSegment> getSegments() {
        return segments;
    }

    public String getName() {
        return name;
    }

    public byte getLeftSteps() {
        return leftSteps;
    }

    public void setSpeed(byte speed) {
        this.speed = speed;
    }

    public byte getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(byte maxSize) {
        this.maxSize = maxSize;
    }

    public void setLeftSteps(byte leftSteps) {
        this.leftSteps = leftSteps;
    }
}
