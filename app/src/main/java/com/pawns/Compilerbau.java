package com.pawns;

import com.example.spybot.R;
import com.pawns.Attack.AttackBuild;

public class Compilerbau extends Pawn{

    public Compilerbau(){
        this.name = "T3INF2002";
        this.speed = 2;
        this.leftSteps = this.speed;
        this.maxSize = 4;
        this.pictureHead = R.drawable.com_head;
        this.pictureTail = R.drawable.com_body;
        this.pictureTailDown = R.drawable.com_down;
        this.pictureTailUp = R.drawable.com_up;
        this.pictureTailLeft = R.drawable.com_left;
        this.pictureTailRight = R.drawable.com_right;

        this.attack1 = new AttackBuild("Trivial",0,(byte)1,(byte)1);
        this.attack2 = new AttackBuild("Titanic",0,(byte)1,(byte)-1);
    }
}
