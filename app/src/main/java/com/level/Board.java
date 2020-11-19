package com.level;

import com.model.AdjacencyList;

public class Board {
    private final int sizeX;
    private final int sizeY;

    private Field[][] board;
    private AdjacencyList<Field> graph;

    public Board(int sizeX, int sizeY ) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        board = new Field[sizeX][sizeY];
        initBoard();
        initGraph();
    }


    private void initBoard() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                board[x][y] = new Field();
            }
        }


        board[1][1].setStatus(true);
        board[1][0].setStatus(true);
        board[1][2].setStatus(true);
        board[0][1].setStatus(true);
        board[2][1].setStatus(true);

        board[0][0].setStatus(true);
        board[15][7].setStatus(true);
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
                    if (left >= 0 && left < sizeY) {
                        neighbour = board[left][y];
                        if (neighbour.getStatus()) {
                            graph.addEdge(middle, neighbour);
                        }
                    }

                    right = x + 1;
                    if (right >= 0 && right < sizeY) {
                        neighbour = board[right][y];
                        if (neighbour.getStatus()) {
                            graph.addEdge(middle, neighbour);
                        }
                    }
                }
            }
        }
    }


}
