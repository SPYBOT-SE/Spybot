package com.pawns;

import com.level.Field;

import static java.lang.Math.abs;

public class AttackHeal extends Attack{


    public AttackHeal(byte range, byte magnitude) {
        super(range, magnitude);
    }

    @Override
    void performAttack(Pawn target) {


        if(magnitude < 0) {
            for(int i = 0; i < abs(magnitude); i++) {

                PawnSegment segment = target.getSegments().get(1);
                Field segField = segment.getField();
                segField.setSegment(null);
                target.getSegments().remove(1);

            }
        }
    }
}
