package com.pawns;

import java.util.ArrayList;

public abstract class Pawn {

    private int speed;
    private int mvRange;
    private int maxHealth;

    private Attack attack1;
    private Attack attack2;

    public Pawn() {

    }

    abstract void move();
    abstract void attack();
}
