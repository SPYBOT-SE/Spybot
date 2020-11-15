package com.level;


/**
 *  Class representing one field of the game board.
 */
public class Field {
    private boolean status; //active or inactive

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean newStatus) {
        this.status = newStatus;
    }
}
