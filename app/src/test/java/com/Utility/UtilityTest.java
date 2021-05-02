package com.Utility;

import com.level.Board;
import com.level.Field;
import com.model.AdjacencyList;
import com.utility.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;


class UtilityTest {

    byte[][] testLvl = {
            {4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 4, 1, 5, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}

    };

    @Test
    void existingStartAndGoal() {
        AdjacencyList<Field> graph = new AdjacencyList<>();
        Field v1 = new Field(0,true, (short) 0,(short) 0);
        Field v2 = new Field(1,true, (short) 0,(short) 0);

        Assertions.assertThrows(NoSuchElementException.class, () -> Utility.getShortestPath(graph, v1, v2));

        graph.addVertex(v1);
        Assertions.assertThrows(NoSuchElementException.class, () -> Utility.getShortestPath(graph, v1, v2));

        graph.removeVertex(v1);
        graph.addVertex(v2);
        Assertions.assertThrows(NoSuchElementException.class, () -> Utility.getShortestPath(graph, v1, v2));
    }

    @Test
    void moveTest() {
        Board board = new Board(testLvl);

    }


}
