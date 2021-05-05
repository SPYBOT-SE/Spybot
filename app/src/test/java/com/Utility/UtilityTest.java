package com.Utility;

import com.level.Board;
import com.level.Field;
import com.model.AdjacencyList;
import com.model.Direction;
import com.pawns.BodyType;
import com.pawns.Pawn;
import com.utility.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;


class UtilityTest {

    int[][] testLvl = {
            {4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
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
    void moveOneRight() {
        Board board = new Board(testLvl);
        Pawn pawn = board.pawnsOnBoard.get(0);

        Field field1 = board.getField((short) 0, (short) 0);
        Field field2 = board.getField((short) 0, (short) 1);

        pawn.move(field1, field2, Direction.RIGHT );

        Assertions.assertNotNull(field1.getSegment());
        Assertions.assertNotNull(field2.getSegment());

        Assertions.assertEquals(field1.getSegment().getBodyType(), BodyType.TailRight);
        Assertions.assertEquals(field2.getSegment().getBodyType(), BodyType.Head);
    }

    @Test
    void moveOne() {
        Board board = new Board(testLvl);
        Pawn pawn = board.pawnsOnBoard.get(0);

        Field field1 = board.getField((short) 0, (short) 0);
        Field field2 = board.getField((short) 0, (short) 1);

        pawn.move(field1, field2, Direction.RIGHT );

        Assertions.assertNotNull(field1.getSegment());
        Assertions.assertNotNull(field2.getSegment());

        Assertions.assertEquals(field1.getSegment().getBodyType(), BodyType.TailRight);
        Assertions.assertEquals(field2.getSegment().getBodyType(), BodyType.Head);
    }


}
