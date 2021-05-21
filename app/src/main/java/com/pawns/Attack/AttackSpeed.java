package com.pawns.Attack;

import com.pawns.Pawn;

public class AttackSpeed extends Attack{

    public AttackSpeed(byte range, byte magnitude) {
        super(range, magnitude);
    }

    @Override
    public void performAttack(Pawn target) {
        target.setSpeed((byte) (target.getSpeed() - magnitude));
    }
}
