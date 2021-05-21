package com.pawns.Attack;

import com.pawns.Pawn;

public abstract class Attack {

    protected byte range;
    protected byte magnitude;
    protected boolean hasBeenUsed;

    public Attack(byte range, byte magnitude) {
        this.range = range;
        this.magnitude = magnitude;
    }

    public abstract void performAttack(Pawn target);


    public boolean GetAttackFlag() {
        return hasBeenUsed;
    }

    public void SetAttackFlag(boolean hasBeenUsed) {
        this.hasBeenUsed = hasBeenUsed;
    }
}
