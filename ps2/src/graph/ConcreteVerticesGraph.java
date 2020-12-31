/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();

    // Abstraction function:
    // represents the a string graph by vertex list
    // Representation invariant:
    // each vertex target must be in vertex list
    // each vertex source is different list
    // Safety from rep exposure:
    // All fields are private, and all types in the rep are immutable.

    /**
     * Make a new ConcreteVerticesGraph .
     */
    public ConcreteVerticesGraph() {
        checkRep();
    }

    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
        List<String> vertexName = new ArrayList<>();
        for (Vertex vertex : vertices) {
            assert !vertexName.contains(vertex.getSource());
            vertexName.add(vertex.getSource());
        }
        for (Vertex vertex : vertices) {
            assert vertexName.containsAll(vertex.targetsMap().keySet());
        }

    }

    @Override
    public boolean add(String vertex) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public int set(String source, String target, int weight) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public boolean remove(String vertex) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Set<String> vertices() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Map<String, Integer> sources(String target) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Map<String, Integer> targets(String source) {
        throw new RuntimeException("not implemented");
    }

    // TODO toString()

}

/**
 * TODO specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Vertex {

    private String source;
    private Map<String, Integer> targets;

    // Abstraction function:
    // represents the a vertex by a source and a map of targets.
    // Representation invariant:
    // the targets map does not contain a key with source name.
    // Safety from rep exposure:
    // All fields are private.

    /**
     * Make a new Edge.
     * 
     * @param source value
     */
    public Vertex(String source) {
        this.source = source;
        targets = new HashMap<>();
        checkRep();
    }

    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
        assert !targets.containsKey(source);
        for (Map.Entry<String, Integer> entry : targets.entrySet()) {
            assert entry.getValue() > 0;
        }
    }

    // TODO methods
    /**
     * @return source value
     */
    public String getSource() {
        checkRep();
        return source;
    }

    /**
     * Add, change, or remove a weighted directed edge in this graph. If weight is
     * nonzero, add an edge or update the weight of that edge; If weight is zero,
     * remove the edge if it exists (the graph is not otherwise modified).
     * 
     * @param target label of the target vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such edge
     */
    public Integer setTarget(String target, Integer weighted) {
        checkRep();
        Integer setTargetflag = 0;
        if (weighted > 0) {
            setTargetflag = weighted;
            targets.put(target, weighted);
        } else if (weighted == 0) {

        }

        return setTargetflag;
    }

    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.
     * 
     * @param source a label
     * @return a map where the key set is the set of labels of vertices such that
     *         this graph includes an edge from source to that vertex, and the value
     *         for each key is the (nonzero) weight of the edge from source to the
     *         key
     */
    public Map<String, Integer> targetsMap() {
        checkRep();
        return new HashMap<String, Integer>(targets);
    };

    @Override
    public String toString() {
        checkRep();
        String result = source + "\n";
        for (Map.Entry<String, Integer> entry : targets.entrySet()) {
            result += "---" + entry.getValue() + "---> " + entry.getKey() + "\n";
        }
        return result;
    }

}
