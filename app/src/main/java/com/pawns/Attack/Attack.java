package com.pawns.Attack;

import com.pawns.Pawn;

public abstract class Attack {

    protected byte range;
    protected byte magnitude;
    protected boolean canAttack;

    public Attack(byte range, byte magnitude) {
        this.range = range;
        this.magnitude = magnitude;
    }

    public abstract void performAttack(Pawn target);


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
}
