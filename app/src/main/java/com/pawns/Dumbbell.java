package com.pawns;

import com.example.spybot.R;
import com.level.Board;
import com.pawns.Attack.AttackSize;
import com.pawns.Attack.AttackSpeed;

public class Dumbbell extends Pawn {

    public Dumbbell() {
        this.name = "Hantel";
        this.speed = 5;
        this.leftSteps = this.speed;
        this.maxSize = 8;
        this.pictureHead = R.drawable.hantel_head;
        this.pictureTail = R.drawable.hantel_body;
        this.pictureTailDown = R.drawable.hantel_body_down;
        this.pictureTailUp = R.drawable.hantel_body_up;
        this.pictureTailLeft = R.drawable.hantel_body_left;
        this.pictureTailRight = R.drawable.hantel_body_right;

        this.attack1 = new AttackSize((byte) 1, (byte) 1);
        this.attack2 = new AttackSpeed((byte) 1, (byte) 1);

    }

}
