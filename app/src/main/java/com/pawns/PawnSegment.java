package com.pawns;

/**
 * Class represents one part of a pawn on the field
 */
public class PawnSegment {

    private Pawn pawn;

    private boolean isHead;

    public PawnSegment(Pawn pawn, boolean isHead) {
        this.pawn = pawn;
        this.isHead = isHead;
    }

    public boolean isHead() {
        return isHead;
    }

    public Pawn getPawn() {
        return pawn;
    }
}
