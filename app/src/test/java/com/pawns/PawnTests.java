package com.pawns;

import com.level.Board;
import com.level.Field;
import com.model.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PawnTests {

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
    void moveOneDown() {
        Board board = new Board(testLvl);
        Pawn pawn = board.pawnsOnBoard.get(0);

        Field field1 = board.getField((short) 0, (short) 0);
        Field field3 = board.getField((short) 1, (short) 0);

        pawn.move(field1, field3, Direction.DOWN );

        Assertions.assertNotNull(field1.getSegment());
        Assertions.assertNotNull(field3.getSegment());

        Assertions.assertEquals(field1.getSegment().getBodyType(), BodyType.TailDown);
        Assertions.assertEquals(field3.getSegment().getBodyType(), BodyType.Head);
    }
}
