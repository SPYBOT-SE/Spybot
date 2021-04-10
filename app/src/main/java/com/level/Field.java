package com.level;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.example.spybot.R;
import com.pawns.PawnSegment;

/**
 *  Class representing one field of the game board.
 */
public class Field {

    private Highlighting highlighting = Highlighting.Empty;

    private Drawable[] layerView = new Drawable[3];

    //active or inactive
    private boolean enabled = true;

    //id of button
    private int id;

    // Segment of a pawn
    private PawnSegment segment = null;


    public PawnSegment getSegment() {
        return segment;
    }

    public void setSegment(PawnSegment segment) {
        this.segment = segment;
    }



    /**
     * Constructor of a Field
     * @param id
     */
    public Field(int id) {
        this.id = id;
    }

    public Field(int id, boolean enabled) {
        this.id = id;
        this.enabled = enabled;
    }

    public Field(int id, boolean enabled, Drawable background) {
        this.id = id;
        this.enabled = enabled;
        this.layerView[0] = background;


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

    public Drawable[] getLayerView() {
        return layerView;
    }

    public void setHighlightingView(Drawable highlighting) {
        this.layerView[2] = highlighting;
    }

    public void setSegmentView(int segment) {
        this.layerView[1].;
    }

}
