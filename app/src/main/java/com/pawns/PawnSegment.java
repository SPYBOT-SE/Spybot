package com.pawns;

import com.level.Field;

/**
 * Class represents one part of a pawn on the field
 */
public class PawnSegment {

    private Pawn pawn;

    private BodyType bodyType;

    private Field field;

    public PawnSegment(Pawn pawn, boolean isHead, Field field) {
        this.pawn = pawn;
        this.bodyType = BodyType.Head;
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Pawn getPawn() {
        return pawn;
    }
}
