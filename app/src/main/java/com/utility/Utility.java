package com.utility;

import com.level.Board;
import com.level.Field;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

public class Utility {

    public static ArrayList<Field> getFieldsInRange(Board board, int id, int range) {
        Field origin = board.getFieldById(id);
        ArrayDeque<Field> deque = new ArrayDeque<>();
        deque.add(origin);

        ArrayList<Field> list;
        HashSet<Field> fieldsInRange = new HashSet<>();

        int neighboursCount = 1;
        int added;
        while (range-- > 0) {
            added = 0;
            while (neighboursCount-- > 0) {
                list = board.getGraph().getNeighbours(deque.getFirst());
                deque.addAll(list);
                fieldsInRange.addAll(list);
                added += list.size();
                deque.pop();
            }
            neighboursCount = added;
        }


        return new ArrayList<>(fieldsInRange);
    }
}
