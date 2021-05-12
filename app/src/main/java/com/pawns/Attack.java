package com.pawns;

public abstract class Attack {

    protected byte range;
    protected byte magnitude;

    public Attack(byte range, byte magnitude) {
        this.range = range;
        this.magnitude = magnitude;
    }

    abstract void performAttack(Pawn target);

}
