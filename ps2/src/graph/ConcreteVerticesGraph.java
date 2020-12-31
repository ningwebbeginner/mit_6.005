/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
            assert !vertexName.contains(vertex.getLabel());
            vertexName.add(vertex.getLabel());
        }
        for (Vertex vertexi : vertices) {
            for(Vertex vertexj : vertices) {
                if(!vertexi.getLabel().equals(vertexj.getLabel())) {
                    for (Map.Entry<String, Integer> entry : vertexi.targetsMap().entrySet()) {
                        if(vertexj.sourcesMap().entrySet().contains(entry.getKey())) {
                            assert (int) vertexj.sourcesMap().get(entry.getKey()) 
                                        == (int)entry.getValue();
                        }
                    }
                }
            }
        }
        

    }

    @Override
    public boolean add(String vertex) {
        boolean include = false;
        for(Vertex eachVertex: vertices) {
            if(eachVertex.getLabel().equals(vertex)) {
                include = true;
            }
        }
        if(!include) {
            vertices.add(new Vertex(vertex));
        }
        checkRep();
        return include;
    }

    @Override
    public int set(String source, String target, int weight) {
        int previouWeight = 0;
        this.add(source);
        this.add(target);
        for(Vertex eachVertex: vertices) {
            if(eachVertex.getLabel().equals(source)) {
                previouWeight = eachVertex.setTarget(target, weight);
            }
            if(eachVertex.getLabel().equals(target)) {
                previouWeight = eachVertex.setSource(source, weight);
            }      
        }
        
        checkRep();
        return previouWeight;
    }

    @Override
    public boolean remove(String vertex) {
        boolean include = false;
        Iterator<Vertex> vertexIterator = vertices.iterator();
        while (vertexIterator.hasNext()) {
            Vertex vertexNext = vertexIterator.next();
            if(vertexNext.getLabel().equals(vertex)) {
                vertexIterator.remove();
                include = true;
                }
            else {
                include = include || vertexNext.setSource(vertex, 0) != 0;
                include = include || vertexNext.setTarget(vertex, 0) != 0;
            }
        }
        return include;
    }

    @Override
    public Set<String> vertices() {
        Set<String> labelSet = new HashSet<>();
        for(Vertex vertex : vertices) {
            labelSet.add(vertex.getLabel());
        }
        
        return labelSet;
    }

    @Override
    public Map<String, Integer> sources(String target) {
        checkRep();
        Map<String, Integer> sourceMap = new HashMap<>();
        for(Vertex vertex:vertices) {
            if(vertex.getLabel().equals(target)) {
                return vertex.sourcesMap();
            }
        }
        return sourceMap;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        checkRep();
        Map<String, Integer> targetMap = new HashMap<>();
        for(Vertex vertex:vertices) {
            if(vertex.getLabel().equals(source)) {
                return vertex.targetsMap();
            }
        }
        return targetMap;
    }

    // TODO toString()
    @Override
    public String toString() {
        checkRep();
        String result = "";
        for(Vertex vertex: vertices) {
               result += vertex.getLabel() + "\n";
               for (Map.Entry<String, Integer> entry : vertex.targetsMap().entrySet()) {
                   result += vertex.getLabel() + " ---" + entry.getValue() + "---> " + entry.getKey() + "\n";
               }
        }
        return result;
    }

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

    private String vertex;
    private Map<String, Integer> sources;
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
        this.vertex = source;
        sources = new HashMap<>();
        targets = new HashMap<>();
        checkRep();
    }

    // Check that the rep invariant is true
    // *** Warning: this does nothing unless you turn on assertion checking
    // by passing -enableassertions to Java
    private void checkRep() {
        assert !targets.containsKey(vertex);
        for (Map.Entry<String, Integer> entry : targets.entrySet()) {
            assert entry.getValue() > 0;
        }
        for (Map.Entry<String, Integer> entry : sources.entrySet()) {
            assert entry.getValue() > 0;
        }
    }

    // TODO methods
    /**
     * @return source value
     */
    public String getLabel() {
        checkRep();
        return vertex;
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
    public int setTarget(String target, Integer weighted) {
        Integer setTargetflag = 0;
        if (weighted != 0) {
            setTargetflag = targets.put(target, weighted);
        } else {
            setTargetflag = targets.remove(target); 
        }
        setTargetflag = setTargetflag != null ? setTargetflag : 0;  

        checkRep();
        return setTargetflag;
    }
    
    /**
     * Add, change, or remove a weighted directed edge in this graph.
     * If weight is nonzero, add an edge or update the weight of that edge;
     * vertices with the given labels are added to the graph if they do not
     * already exist.
     * If weight is zero, remove the edge if it exists (the graph is not
     * otherwise modified).
     * 
     * @param source label of the source vertex
     * @param target label of the target vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     */
    public int setSource(String source, Integer weighted) {
        
        Integer setSourceflag = 0;
        if (weighted != 0) {
            setSourceflag = sources.put(source, weighted);
        } else {
            setSourceflag = sources.remove(source); 
        }
        setSourceflag = setSourceflag != null ? setSourceflag : 0;  

        checkRep();
        return setSourceflag;
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
    
    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.
     * 
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such that
     *         this graph includes an edge from source to that vertex, and the value
     *         for each key is the (nonzero) weight of the edge from source to the
     *         key
     */
    public Map<String, Integer> sourcesMap() {
        checkRep();
        return new HashMap<String, Integer>(sources);
    };

    @Override
    public String toString() {
        checkRep();
        String result = vertex + "\n";
        for (Map.Entry<String, Integer> entry : targets.entrySet()) {
            result += "---" + entry.getValue() + "---> " + entry.getKey() + "\n";
        }
        for (Map.Entry<String, Integer> entry : sources.entrySet()) {
            result += "---" + entry.getValue() + "---> " + entry.getKey() + "\n";
        }
        return result;
    }

}
