package com.level;

import java.util.ArrayList;
import java.util.HashMap;

public class levelSingle {

    private static boolean initialized = false;
    public static int[][] getLevel(int levelID) {

        if(!initialized) {
            initializeLevels();
        }

        if(!levels.containsKey(levelID)) {
            return Error;
        }

        return levels.get(levelID);
    }
    /**
     * Hashmap that maps button IDs to levels
     */
    private static HashMap<Integer, int[][]> levels = new HashMap<>();
    private static void initializeLevels() {
        levels.put(0, Ones);
        levels.put(1, TestLevel1);
        levels.put(2, SPF);
    }

    /*
    Definition:
    -128
    ...     Enemies
    -1
    0   =   Void
    1   =   Empty field
    2   =   Spawn Player
    3   =   Spawn Player
    4   =   Spawn Player
    5   =   Bug
    6   =   Player
    7   =   Player
    8   =   Player
    9   =   Player
    10
    ... =   Credits with different Values
    127

    */
    public final static int[][] Ones = {
            {0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            // {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            // {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            // {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public final static int[][] TestLevel1 = {
            {5, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1 },
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 },
            {1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0 },
            {0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            {0, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1 },
            {0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1 }
    };

    public final static int[][] Error = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            {0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0 },
            {0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0 },
            {0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0 },
            {0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1 },
            {0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0 },
            {0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0 },
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },


    };

    public final static int[][] SPF = {
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
    };
}
