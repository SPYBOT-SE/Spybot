package com.level;

import com.model.AdjacencyList;

public class Board {

    int idCount = 0;



    private int sizeY; //vertical axis
    private int sizeX; //horizontal axis


    private Field[][] board;
    private AdjacencyList<Field> graph;



    public Board(byte[][] level ) {

        initBoard(level);

        initGraph();
    }


    public Field[][] getBoard() {
        return board;
    }

    public AdjacencyList<Field> getGraph() {
        return graph;
    }

    private void initBoard(byte[][] fieldDef) {

        sizeY = fieldDef.length;
        sizeX = fieldDef[0].length;

        board = new Field[sizeX][sizeY];

        for(int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++) {

                board[x][y] = getField(fieldDef[y][x]);

            }

        }


    }

    private Field getField(byte value) {
        Field outField;

        switch (value) {
            case 0:
                outField = new Field(idCount, false);
                break;
            case 1:
                outField = new Field(idCount, true);
                break;
            default:
                throw  new IllegalArgumentException("Unexpected value for field type: " + value);

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


    public Field getFieldById(int id) {
        return board[id%sizeX][id/sizeX];
    }

    public int getSizeX() {
        return sizeX;
    }


    public int getSizeY() {
        return sizeY;
    }

}
