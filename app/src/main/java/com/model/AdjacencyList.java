package com.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AdjacencyList<T> {

    private static class Vertex<S> {
        private final S self;
        private final ArrayList<S> neighbours;

        public Vertex(S vertex) {
            self = vertex;
            neighbours = new ArrayList<>();
        }

        public boolean same(S compare) {
            return self == compare;
        }
    }


    HashSet<Vertex<T>> vertices = new HashSet<>(96);


    public void addVertex(T vertex) {
        Optional<Vertex<T>> v = getVertex(vertex);
        if (!v.isPresent()) {
            vertices.add(new Vertex<T>(vertex));
        }
    }

    public void removeVertex(T remVertex) {
        Vertex<T> current;
        for (Iterator<Vertex<T>> it = vertices.iterator(); it.hasNext(); ) {
            current = it.next();
            if (current.same(remVertex)) {
                it.remove();
            } else {
                for (T neighbour: current.neighbours) {
                    if (neighbour.equals(remVertex)) {
                        current.neighbours.remove(neighbour);
                    }
                }
            }
        }
    }

    public void addEdge(T from, T to) {
        addVertex(from);
        addVertex(to);
        Optional<Vertex<T>> optFromVertex = getVertex(from);
        if (optFromVertex.isPresent()) {
            optFromVertex.get().neighbours.add(to);
        }
    }

    public void removeEdge(T from, T to) {
        Optional<Vertex<T>> optFromVertex = getVertex(from);
        if (optFromVertex.isPresent()) {
            optFromVertex.get().neighbours.remove(to);
        }
    }

    private Optional<Vertex<T>> getVertex(T vertex) {
        Vertex<T> v = null;

        for (Vertex<T> tVertex : vertices) {
            if (tVertex.same(vertex)) {
                v = tVertex;
                break;
            }
        }

        return Optional.ofNullable(v);
    }

    public HashSet<Vertex<T>> getAllVertices() {
        return vertices;
    }

    public ArrayList<T> getNeighbours(T vertex) throws NoSuchElementException {
        Optional<Vertex<T>> optionalVertex = getVertex(vertex);
        if (optionalVertex.isPresent()) {
            return optionalVertex.get().neighbours;
        }
        throw new NoSuchElementException("Vertex not found");
    }
}
