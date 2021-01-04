package com.model;

import java.util.ArrayList;

public class Vertex<T> {
    private final T self;
    private final ArrayList<Vertex<T>> neighbours = new ArrayList<>();
    private int distance = Integer.MAX_VALUE;
    private Vertex<T> predecessor;

    public Vertex() {
        self = null;
    }

    public Vertex(T vertex) {
        self = vertex;
    }

    public ArrayList<Vertex<T>> getNeighbours() {
        return neighbours;
    }

    public T getSelf() {
        return self;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Vertex<T> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertex<T> predecessor) {
        this.predecessor = predecessor;
    }

    public boolean same(T compare) {
        return self == compare;
    }

    public boolean haveSameCores(Vertex<T> compare) {
        return self == compare.self;
    }
}
