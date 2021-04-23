package com.pawns;

import android.graphics.drawable.Drawable;
import com.example.spybot.R;
import com.level.Field;
import com.model.Team;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Pawn {

    protected String name;
    protected Team team;

    protected byte speed;
    protected byte leftSteps;
    // private int mvRange;
    protected byte maxSize;

    private Attack attack1;
    private Attack attack2;

    protected int pictureHead = R.drawable.button_icon;
    protected int pictureTail = R.drawable.button_icon;
    protected int pictureTailUp = R.drawable.button_icon;
    protected int pictureTailDown = R.drawable.button_icon;
    protected int pictureTailRight = R.drawable.button_icon;
    protected int pictureTailLeft = R.drawable.button_icon;


    private LinkedList<PawnSegment> segments = new LinkedList<>();

    public void createSegment(Field field) {
        PawnSegment newSeg = new PawnSegment(this, true, field);
        segments.add(newSeg);
        field.setSegment(newSeg);
    }

    public void mov(Field field) {
        segments = new LinkedList<>();
        PawnSegment seg = new PawnSegment(this, true, field);
        segments.add(seg);
    }

    public boolean move(Field field) {
        if(field.getSegment() != null) {
            return false;
        }

        createSegment(field);

        for(PawnSegment segment: segments) {
            segment.setBodyType(BodyType.Tail);
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

    public Team getTeam(){
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
