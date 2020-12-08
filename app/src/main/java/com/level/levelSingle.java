package com.level;

import java.util.ArrayList;
import java.util.HashMap;

public class levelSingle {

    private static boolean initialized = false;
    public static byte[][] getLevel(int levelID) {

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
    private static HashMap<Integer, byte[][]> levels = new HashMap<>();
    private static void initializeLevels() {
        levels.put(0, Ones);
        levels.put(1, TestLevel1);
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
    public final static byte[][] Ones = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1},
            {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1,1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1}
            // {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            // {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            // {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public final static byte[][] TestLevel1 = {
            {2, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1 },
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 },
            {1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0 },
            {0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1 },
            {0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1 }
    };

    public final static byte[][] Error = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            {0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0 },
            {0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0 },
            {0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0 },
            {0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1 },
            {0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0 },
            {0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0 },
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },


    };
}
