package com.pawns;

import com.level.Field;

/**
 * Class represents one part of a pawn on the field
 */
public class PawnSegment {

    private Pawn pawn;

    private boolean isHead;

    private Field field;

    public PawnSegment(Pawn pawn, boolean isHead, Field field) {
        this.pawn = pawn;
        this.isHead = isHead;
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public boolean isHead() {
        return isHead;
    }

    public void setNotHead() {
        isHead = false;
    }

    public Pawn getPawn() {
        return pawn;
    }
}
