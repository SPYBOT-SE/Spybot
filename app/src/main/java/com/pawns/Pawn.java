package com.pawns;

import android.graphics.drawable.Drawable;
import com.example.spybot.R;
import com.level.Field;
import com.model.Direction;

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

    public void createSegment(Field field, BodyType type) {
        PawnSegment newSeg = new PawnSegment(this, type, field);
        segments.add(newSeg);
        field.setSegment(newSeg);
    }

    public void move(Field from, Field to, Direction direction) {
        if(from.getSegment() == null) {
            return;
        }

        to.setSegment(from.getSegment());


        switch (direction) {
            case UP:
                createSegment(from, BodyType.TailUp);
                break;
            case DOWN:
                createSegment(from, BodyType.TailDown);
                break;
            case LEFT:
                createSegment(from, BodyType.TailLeft);
                break;
            case RIGHT:
                createSegment(from, BodyType.TailRight);
                break;
        }


        while(segments.size() > maxSize) {
            Field segField = segments.get(1).getField();
            segField.setSegment(null);
            segments.remove(1);
        }


        return;

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

    public byte getCurrentSize() {
        return (byte) segments.size();
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
