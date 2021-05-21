package com.pawns.Attack;

import com.pawns.Pawn;

public class AttackSize extends Attack {

    public AttackSize(byte range, byte magnitude) {
        super(range, magnitude);
    }

    @Override
    public void performAttack(Pawn target) {
        target.setMaxSize((byte) (target.getMaxSize() + magnitude));
    }
}
