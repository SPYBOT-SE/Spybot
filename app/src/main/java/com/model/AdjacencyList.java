package com.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AdjacencyList<T> {

    /*private static class Vertex<S> {
        private final S self;
        private final ArrayList<S> neighbours;

        public Vertex(S vertex) {
            self = vertex;
            neighbours = new ArrayList<>();
        }

        public boolean same(S compare) {
            return self == compare;
        }
    }*/


    HashSet<Vertex<T>> vertices = new HashSet<>(96);


    public void addVertex(T vertex) {
        Optional<Vertex<T>> v = getVertex(vertex);
        if (!v.isPresent()) {
            vertices.add(new Vertex<>(vertex));
        }
    }

    public void addVertex(Vertex<T> vertex) {
        for (Vertex<T> v : vertices) {
            if (v.same(vertex.getSelf())) {
                return;
            }
        }
        vertices.add(vertex);
    }

    public void removeVertex(T remVertex) {
        Vertex<T> current;
        for (Iterator<Vertex<T>> it = vertices.iterator(); it.hasNext(); ) {
            current = it.next();
            if (current.same(remVertex)) {
                it.remove();
            } else {
                for (Vertex<T> neighbour: current.getNeighbours()) {
                    if (neighbour.same(remVertex)) {
                        current.getNeighbours().remove(neighbour);
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
            optFromVertex.get().getNeighbours().add(new Vertex<>(to));
        }
    }

    public void removeEdge(T from, T to) {
        Optional<Vertex<T>> optFromVertex = getVertex(from);
        if (optFromVertex.isPresent()) {
            optFromVertex.get().getNeighbours().remove(to);
        }
    }

    public Optional<Vertex<T>> getVertex(T vertex) {
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

    public ArrayList<Vertex<T>> getNeighbours(T vertex) throws NoSuchElementException {
        Optional<Vertex<T>> optionalVertex = getVertex(vertex);
        if (optionalVertex.isPresent()) {
            return optionalVertex.get().getNeighbours();
        }
        throw new NoSuchElementException("Vertex not found");
    }

    public ArrayList<T> getNeighbourFields(T vertex) throws NoSuchElementException {
        ArrayList<T> list = new ArrayList<>();
        try {
            for (Vertex<T> v : getNeighbours(vertex)) {
                list.add(v.getSelf());
            }
            return list;
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }
}
