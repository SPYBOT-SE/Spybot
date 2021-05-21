package com.pawns.Attack;

import com.level.Field;
import com.pawns.Pawn;

public class AttackSpeed extends Attack{

    public AttackSpeed(String attackName,int icon,  byte range, byte magnitude) {
        super(attackName,icon,  range, magnitude);
    }

    @Override
    public void performAttack(Field target) {
        target.getSegment().getPawn().setSpeed((byte) (target.getSegment().getPawn().getSpeed() - magnitude));
    }
}
