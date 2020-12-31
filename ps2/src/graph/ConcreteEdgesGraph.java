/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    // represents the a L graph
    // Representation invariant:
    // vertices each has a different L.
    // edges each edge contain two vertices in set.
    // Safety from rep exposure:
    // All fields are private, and all types in the rep are immutable.

    /**
     * Make a new ConcreteEdgesGraph .
     */
    public ConcreteEdgesGraph() {
        checkRep();
    }

    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
        for (Edge<L> edge : edges) {
            assert vertices.containsAll(Arrays.asList(edge.getSource(), edge.getTarget()));
        }
    }

    @Override
    public boolean add(L vertex) {
        return vertices.add(vertex);
    }

    @Override
    public int set(L source, L target, int weight) {
        int result = 0;
        int edgeIndex = -1;

        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                edgeIndex = edges.indexOf(edge);
            }

        }

        if (edgeIndex != -1) {
            result = edges.get(edgeIndex).getWeight();
            edges.remove(edgeIndex);
        }

        if (weight != 0) {
            edges.add(new Edge<L>(source, target, weight));
            vertices.add(source);
            vertices.add(target);
        }

        return result;
    }

    @Override
    public boolean remove(L vertex) {
        boolean result = false;
        result = vertices.remove(vertex);
        if (result) {
            Iterator<Edge<L>> edgeIterator = edges.iterator();
            while (edgeIterator.hasNext()) {
                Edge<L> nextedge = edgeIterator.next();
                if (nextedge.getSource().equals(vertex) 
                        || nextedge.getTarget().equals(vertex)) {
                    edgeIterator.remove();
                }
            }
         
        }
        return result;
    }

    @Override
    public Set<L> vertices() {
        return new HashSet<L>(vertices);
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> result = new HashMap<>();
        if (vertices.contains(target)) {
            List<Integer> sourceIndex = new ArrayList<>();
            for (Edge<L> edge : edges) {
                if (edge.getTarget().equals(target)) {
                    sourceIndex.add(edges.indexOf(edge));
                }

            }
            for (Integer edgeIndex : sourceIndex) {
                Edge<L> edge = edges.get(edgeIndex);
                result.put(edge.getSource(), edge.getWeight());
            }
        }
        return result;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        Map<L, Integer> result = new HashMap<>();
        if (vertices.contains(source)) {
            List<Integer> sourceIndex = new ArrayList<>();
            for (Edge<L> edge : edges) {
                if (edge.getSource().equals(source)) {
                    sourceIndex.add(edges.indexOf(edge));
                }
            }
            for (Integer edgeIndex : sourceIndex) {
                Edge<L> edge = edges.get(edgeIndex);
                result.put(edge.getTarget(), edge.getWeight());
            }
        }
        return result;

    }

    @Override
    public String toString() {
        String result = "";
        for (L vertex : vertices) {
            result += vertex + "\n";
        }
        for (Edge<L> edge : edges) {
            result += edge.toString() + "\n";
        }
        return result;
    }
}

/**
 * Represents the the weighted edge of a L graph. Immutable. This class is
 * internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge<L> {

    private L source;
    private L target;
    private int weight;

    // Abstraction function:
    // represents the the edge of a L graph.
    // Representation invariant:
    // source value does not equal to target value.
    // weight > 0
    // Safety from rep exposure:
    // All fields are private, and all types in the rep are immutable.

    /**
     * Make a new Edge.
     * 
     * @param n value
     * @param n value
     */
    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
        assert !source.equals(target);
        assert weight > 0;
    }

    // TODO methods
    /**
     * get the source value
     * 
     * @return source value
     */
    public L getSource() {
        checkRep();
        return source;
    }

    /**
     * get the target value
     * 
     * @return target value
     */
    public L getTarget() {
        checkRep();
        return target;
    }

    /**
     * get the weight value
     * 
     * @return weight value
     */
    public int getWeight() {
        checkRep();
        return weight;
    }

    @Override
    public String toString() {
        checkRep();
        return source + " ---" + weight + "---> " + target;
    }

}
