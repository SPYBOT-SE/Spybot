package com.pawns;

public class Body {

    private Position position;
    private Pawn head;

    public Body(Pawn head, int x, int y) {
        this.position = new Position(x,y);
    }

}
