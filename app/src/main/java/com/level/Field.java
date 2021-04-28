package com.level;


import android.content.Context;
import android.content.MutableContextWrapper;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.example.spybot.R;
import com.pawns.PawnSegment;

/**
 *  Class representing one field of the game board.
 */
public class Field{

    public final short x;
    public final short y;

    private Highlighting highlighting = Highlighting.Empty;

    //active or inactive
    private boolean enabled = true;

    //id of button
    private int id;

    // Segment of a pawn
    private PawnSegment segment = null;

    //Spawn Area
    byte spawner = 0;


    public PawnSegment getSegment() {
        return segment;
    }

    public void setSegment(PawnSegment segment) {
        this.segment = segment;
    }


    public Field(int id, boolean enabled, short x, short y) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.enabled = enabled;
    }

    public boolean getStatus() {
        return enabled;
    }

    public void setStatus(boolean newStatus) {
        this.enabled = newStatus;
    }

    public int getId() {return id; }

    public Highlighting getHighlighting() {
        return highlighting;
    }

    public void setHighlighting(Highlighting highlighting) {
        this.highlighting = highlighting;
    }

    public byte getSpawner() {
        return spawner;
    }

    public void setSpawner(byte spawner) {
        this.spawner = spawner;
    }
}
