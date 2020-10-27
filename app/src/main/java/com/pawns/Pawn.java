package com.pawns;

public abstract class Pawn {

    private int speed;
    private int mvRange;
    private int maxHealth;


    Attack attack1;
    Attack attack2;

    abstract void move();
    abstract void attack();
}
