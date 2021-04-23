package com.model;

import com.pawns.Pawn;

public class SelectedInfo {
    private Pawn pawn;
    private int fieldId;

    public SelectedInfo() {
        pawn = null;
        fieldId = -1;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }
}
