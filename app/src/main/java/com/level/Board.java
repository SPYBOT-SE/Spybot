package com.level;

public class Board {
    private int sizeX;
    private int sizeY;

    Field[][] board;

    Board(int sizeX, int sizeY ) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.board = new Field[sizeX][sizeY];
    }




}
