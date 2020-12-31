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
public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction function:
    // represents the a string graph
    // Representation invariant:
    // vertices each has a different string.
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
        for (Edge edge : edges) {
            assert vertices.containsAll(Arrays.asList(edge.getSource(), edge.getTarget()));
        }
    }

    @Override
    public boolean add(String vertex) {
        return vertices.add(vertex);
    }

    @Override
    public int set(String source, String target, int weight) {
        int result = 0;
        int edgeIndex = -1;

        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                edgeIndex = edges.indexOf(edge);
            }

        }

        if (edgeIndex != -1) {
            result = edges.get(edgeIndex).getWeight();
            edges.remove(edgeIndex);
        }

        if (weight != 0) {
            edges.add(new Edge(source, target, weight));
            vertices.add(source);
            vertices.add(target);
        }

        return result;
    }

    @Override
    public boolean remove(String vertex) {
        boolean result = false;
        result = vertices.remove(vertex);
        if (result) {
            Iterator<Edge> edgeIterator = edges.iterator();
            while (edgeIterator.hasNext()) {
                Edge nextedge = edgeIterator.next();
                if (nextedge.getSource().equals(vertex) 
                        || nextedge.getTarget().equals(vertex)) {
                    edgeIterator.remove();
                }
            }
         
        }
        return result;
    }

    @Override
    public Set<String> vertices() {
        return new HashSet<String>(vertices);
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> result = new HashMap<>();
        if (vertices.contains(target)) {
            List<Integer> sourceIndex = new ArrayList<>();
            for (Edge edge : edges) {
                if (edge.getTarget().equals(target)) {
                    sourceIndex.add(edges.indexOf(edge));
                }

            }
            for (Integer edgeIndex : sourceIndex) {
                Edge edge = edges.get(edgeIndex);
                result.put(edge.getSource(), edge.getWeight());
            }
        }
        return result;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> result = new HashMap<>();
        if (vertices.contains(source)) {
            List<Integer> sourceIndex = new ArrayList<>();
            for (Edge edge : edges) {
                if (edge.getSource().equals(source)) {
                    sourceIndex.add(edges.indexOf(edge));
                }
            }
            for (Integer edgeIndex : sourceIndex) {
                Edge edge = edges.get(edgeIndex);
                result.put(edge.getTarget(), edge.getWeight());
            }
        }
        return result;

    }

    @Override
    public String toString() {
        String result = "";
        for (String vertex : vertices) {
            result += vertex + "\n";
        }
        for (Edge edge : edges) {
            result += edge.toString() + "\n";
        }
        return result;
    }
}

/**
 * Represents the the weighted edge of a string graph. Immutable. This class is
 * internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge {

    private String source;
    private String target;
    private int weight;

    // Abstraction function:
    // represents the the edge of a string graph.
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
    public Edge(String source, String target, int weight) {
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
    public String getSource() {
        checkRep();
        return source;
    }

    /**
     * get the target value
     * 
     * @return target value
     */
    public String getTarget() {
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
