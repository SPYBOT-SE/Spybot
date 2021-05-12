package com.pawns;

import com.example.spybot.R;

public class Bug extends Pawn {

    public Bug() {
        this.team = 0;
        this.name = "Bug";
        this.speed = 4;
        this.leftSteps = this.speed;
        this.maxSize = 3;
        this.pictureHead = R.drawable.button_icon_bug;
        this.pictureTail = R.drawable.button_icon_bug;
        this.attack1 = new AttackHeal((byte) 1, (byte) -1);
        this.attack2 = new AttackSpeed((byte) 1, (byte) -1);

    }



}
