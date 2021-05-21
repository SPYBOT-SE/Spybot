package com.pawns.Attack;

import com.pawns.Pawn;

public abstract class Attack {

    protected byte range;
    protected byte magnitude;

    public Attack(byte range, byte magnitude) {
        this.range = range;
        this.magnitude = magnitude;
    }

    public abstract void performAttack(Pawn target);

}
