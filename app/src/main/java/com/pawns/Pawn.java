package com.pawns;

import com.example.spybot.R;
import com.level.Board;
import com.level.Field;
import com.model.Direction;
import com.pawns.Attack.Attack;

import java.util.LinkedList;

public abstract class Pawn {

    protected String name = "Template";
    protected byte team = 0;

    protected byte speed = 0;
    protected byte leftSteps = 0;

    protected byte maxSize = 0;

    protected Attack attack1;
    protected Attack attack2;

    public int icon = R.drawable.bug;
    public int pictureHead = R.drawable.hantel_head;
    public int pictureTail = R.drawable.hantel_body;
    public int pictureTailUp = R.drawable.hantel_body_up;
    public int pictureTailDown = R.drawable.hantel_body_down;
    public int pictureTailRight = R.drawable.hantel_body_right;
    public int pictureTailLeft = R.drawable.hantel_body_left;


    private final LinkedList<PawnSegment> segments = new LinkedList<>();

    public void createSegment(Field field, BodyType type) {
        PawnSegment newSeg = new PawnSegment(this, type, field);
        segments.add(newSeg);
        field.setSegment(newSeg);
    }

    public void die() {

        Field field = this.getSegments().get(0).getField();
        field.setSegment(null);
        this.segments.remove(0);

        field.board.pawnsOnBoard.remove(this);
        field.board.pawnsInTeam2.remove(this);
        field.board.pawnsInTeam1.remove(this);
    }

    public void move(Field from, Field to, Direction direction) {
        if(from.getSegment() == null) {
            return;
        }
        //Pawn pawn = from.getSegment().getPawn();

        if(this.getLeftSteps() <= 0) {
            return;
        }

        this.setLeftSteps((byte) (this.getLeftSteps()-1));
        to.setSegment(from.getSegment());

        this.segments.get(0).setField(to);

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
    }

    public void attack1(Field target) {
        this.attack1.performAttack(target);
    }

    public void attack2(Field target) {
        this.attack2.performAttack(target);
    }

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

    public Attack getAttack1() {
        return attack1;
    }

    public Attack getAttack2() {
        return attack2;
    }
}
