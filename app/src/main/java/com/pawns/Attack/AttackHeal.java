package com.pawns.Attack;

import com.level.Field;
import com.pawns.Pawn;
import com.pawns.PawnSegment;

import static java.lang.Math.abs;

public class AttackHeal extends Attack{


    public AttackHeal(String attackName,int icon,  byte range, byte magnitude) {
        super(attackName,icon, range, magnitude);
    }

    @Override
    public void performAttack(Field target) {

        if(magnitude < 0) {
            for(int i = 0; i < abs(magnitude); i++) {
                if(target.getSegment().getPawn().getSegments().size() > 1){
                    PawnSegment segment = target.getSegment().getPawn().getSegments().get(1);
                    Field segField = segment.getField();
                    segField.setSegment(null);
                    target.getSegment().getPawn().getSegments().remove(1);
                }
                else {
                    target.getSegment().getPawn().die();
                    continue;
                }

            }
        }
    }
}
