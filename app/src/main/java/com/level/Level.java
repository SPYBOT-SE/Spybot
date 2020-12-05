package com.level;

public class Level {

    private String lvlName;

    private byte[][] iniField;

    public Level( byte[][] field) {

        this.iniField = field;
    }

    public byte[][] getIniField() {
        return iniField;
    }
}
