package com.pawns;

import android.graphics.drawable.Drawable;
import com.example.spybot.R;
import com.level.Field;

import java.util.LinkedList;

public abstract class Pawn {

    protected String name = "Template";
    protected byte team = 0;

    protected byte speed = 0;
    protected byte leftSteps = 0;
    // private int mvRange;
    protected byte maxSize = 0;

    private Attack attack1;
    private Attack attack2;


    public int pictureHead = R.drawable.hantel_head;
    public int pictureTail = R.drawable.hantel_body;
    public int pictureTailUp = R.drawable.hantel_body_up;
    public int pictureTailDown = R.drawable.hantel_body_down;
    public int pictureTailRight = R.drawable.hantel_body_right;
    public int pictureTailLeft = R.drawable.hantel_body_left;


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

    public byte getTeam(){
        return team;
    }

    public void setTeam(byte team) {
        this.team = team;
    }
}
