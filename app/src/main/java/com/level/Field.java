package com.level;


/**
 *  Class representing one field of the game board.
 */
public class Field {
    private boolean status = true; //active or inactive
    private int id; //id of button


    public Field(int id) {
        this.id = id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean newStatus) {
        this.status = newStatus;
    }

    public int getId() {return id; }
}
