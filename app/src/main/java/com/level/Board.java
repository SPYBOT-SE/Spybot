package com.level;


import android.content.Context;
import android.content.MutableContextWrapper;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.example.spybot.R;
import com.model.AdjacencyList;
import com.pawns.Bug;
import com.pawns.Pawn;

import java.util.ArrayList;

public class Board {

    int idCount = 0;

    ArrayList<Pawn> pawnsOnBoard = new ArrayList<>();


    private int sizeY; //vertical axis
    private int sizeX; //horizontal axis


    private Field[][] board;
    private AdjacencyList<Field> graph;

    Drawable[] backgrounds;

    Resources r;

    public Board(byte[][] level) {

        initBoard(level);

        initGraph();
    }

/*
    public Field[][] getBoard() {
        return board;
    }
*/
    public AdjacencyList<Field> getGraph() {
        return graph;
    }

    private void initBoard(byte[][] fieldDef) {

        sizeY = fieldDef.length;
        sizeX = fieldDef[0].length;

        board = new Field[sizeX][sizeY];

        for (short y = 0; y < sizeY; y++) {
            for (short x = 0; x < sizeX; x++) {

                board[x][y] = getField(fieldDef[y][x],x,y);

            }

        }


    }



    private Field getField(byte value, short x, short y) {
        Field outField = null;

        switch (value) {
            case 0:
                outField = new Field(idCount, false, x,y);
                break;
            case 1:
                outField = new Field(idCount, true,x,y);
                break;
            case 2:
                outField = new Field(idCount, true,x,y);

                Pawn bug = new Bug();
                pawnsOnBoard.add(bug);
                bug.createSegment(outField);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 10:


            default:
                throw new IllegalArgumentException("Unexpected value for field type: " + value);

        }
        idCount++;
        return outField;
    }


    private void initGraph() {
        graph = new AdjacencyList<>();

        int top, bottom, left, right;
        Field middle, neighbour;

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                middle = board[x][y];
                if (middle.getStatus()) {
                    graph.addVertex(middle);

                    top = y - 1;
                    if (top >= 0 && top < sizeY) {
                        neighbour = board[x][top];
                        if (neighbour.getStatus()) {
                            graph.addEdge(middle, neighbour);
                        }
                    }

                    bottom = y + 1;
                    if (bottom >= 0 && bottom < sizeY) {
                        neighbour = board[x][bottom];
                        if (neighbour.getStatus()) {
                            graph.addEdge(middle, neighbour);
                        }
                    }

                    left = x - 1;
                    if (left >= 0 && left < sizeX) {
                        neighbour = board[left][y];
                        if (neighbour.getStatus()) {
                            graph.addEdge(middle, neighbour);
                        }
                    }

                    right = x + 1;
                    if (right >= 0 && right < sizeX) {
                        neighbour = board[right][y];
                        if (neighbour.getStatus()) {
                            graph.addEdge(middle, neighbour);
                        }
                    }
                }
            }
        }
    }

    public Field getField(short x, short y) {
        if(x >= 0 && x < sizeX && y >= 0 && y < sizeY) {
            return board[x][y];
        } else{
            return null;
        }
    }

    public Field getFieldById(int id) {
        return board[id % sizeX][id / sizeX];
    }

    public int getXofField(int id) {
        return 0;
    }

    public int getSizeX() {
        return sizeX;
    }


    public int getSizeY() {
        return sizeY;
    }

}
