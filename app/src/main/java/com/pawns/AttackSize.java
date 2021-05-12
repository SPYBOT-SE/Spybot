package com.pawns;

public class AttackSize extends Attack {

    public AttackSize(byte range, byte magnitude) {
        super(range, magnitude);
    }

    @Override
    void performAttack(Pawn target) {
        target.setMaxSize((byte) (target.getMaxSize() + magnitude));
    }
}
