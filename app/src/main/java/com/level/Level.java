package com.level;

public class Level {

    private String lvlName;

    private int[][] iniField;

    public Level( int[][] field) {

        this.iniField = field;
    }

    public int[][] getIniField() {
        return iniField;
    }
}
