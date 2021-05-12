package com.pawns;

public class AttackSpeed extends Attack{

    public AttackSpeed(byte range, byte magnitude) {
        super(range, magnitude);
    }

    @Override
    void performAttack(Pawn target) {
        target.setSpeed((byte) (target.getSpeed() - magnitude));
    }
}
