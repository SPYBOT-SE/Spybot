package com.pawns;

import com.example.spybot.R;

public class Dumbbell extends Pawn {

    public Dumbbell() {
        this.name = "Hantel";
        this.speed = 5;
        this.leftSteps = this.speed;
        this.maxSize = 3;
        this.pictureHead = R.drawable.button_icon_bug;
        this.pictureTail = R.drawable.button_icon_bug;

    }

    @Override
    void attack() {

    }


}
