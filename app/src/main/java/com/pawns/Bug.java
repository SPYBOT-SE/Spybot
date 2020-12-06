package com.pawns;

public class Bug extends Pawn {

    public Bug() {
        this.name = "Bug";
        this.speed = 4;
        this.leftSteps = this.speed;
        this.maxSize = 3;
    }

    @Override
    void attack() {

    }
}
