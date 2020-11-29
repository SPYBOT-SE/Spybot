package com.level;

import java.util.ArrayList;

public class levelSingle {

    ArrayList<Level> levels;

    public levelSingle() {
        levels = new ArrayList<>();
        addLevels();
    }

    private void addLevels() {

        levels.add((new Level("Test1", (byte)11, (byte)14, Ones)));
        levels.add((new Level("Test1", (byte)8, (byte)12, TestLevel1)));
    }

    /*
    Definition:
    -128
    ...     Enemies
    -1
    0   =   Void
    1   =   Empty field
    2   =   Player
    3   =   Player
    4   =   Player
    5   =   Player
    6   =   Player
    7   =   Player
    8   =   Player
    9   =   Player
    10
    ... =   Credits with different Values
    127




    */
    final byte[][] Ones = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    final byte[][] TestLevel1 = {
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, },
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, },
            {1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, },
            {0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, },
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, },
            {0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, }
    };
}
