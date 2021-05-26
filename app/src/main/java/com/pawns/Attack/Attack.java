package com.pawns.Attack;

import com.level.Field;

public abstract class Attack {

    protected String nameOfAttack;
    protected int resource;
    protected byte range;
    protected byte magnitude;
    protected boolean canAttack;

    public Attack(String attackName,int icon,  byte range, byte magnitude) {
        this.nameOfAttack = attackName;
        this.resource = icon;
        this.range = range;
        this.magnitude = magnitude;
    }

    public abstract void performAttack(Field target);


    public boolean canAttack() {
        return canAttack;
    }

    public void SetAttackFlag(boolean hasBeenUsed) {
        this.canAttack = hasBeenUsed;
    }

    public byte getRange() {
        return range;
    }

    public void setRange(byte range) {
        this.range = range;
    }

    public byte getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(byte magnitude) {
        this.magnitude = magnitude;
    }

    public String getNameOfAttack() {
        return nameOfAttack;
    }

    public int getResource() {
        return resource;
    }
}
