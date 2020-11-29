package com.level;

public class Level {

    private String lvlName;

    private byte x;
    private byte y;

    private byte[][] iniField;

    public Level(String name, byte x, byte y, byte[][] field) {
        if(chkSize(x,y, field)) {
            throw new IllegalArgumentException("Level has wrong size parameters");
        }
        this.lvlName = name;
        this.x = x;
        this.y = y;
        this.iniField = field;
    }

    // Check if Field has right size
    private boolean chkSize(byte x, byte y, byte[][] field) {
        return x == field.length && y == field[0].length;

    }




}
