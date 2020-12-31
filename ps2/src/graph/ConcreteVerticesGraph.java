/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
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
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();

    // Abstraction function:
    // represents the a L graph by vertex list
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
        List<L> vertexName = new ArrayList<>();
        for (Vertex<L> vertex : vertices) {
            assert !vertexName.contains(vertex.getLabel());
            vertexName.add(vertex.getLabel());
        }
        for (Vertex<L> vertexi : vertices) {
            for(Vertex<L> vertexj : vertices) {
                if(!vertexi.getLabel().equals(vertexj.getLabel())) {
                    for (Map.Entry<L, Integer> entry : vertexi.targetsMap().entrySet()) {
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
    public boolean add(L vertex) {
        boolean include = false;
        for(Vertex<L> eachVertex: vertices) {
            if(eachVertex.getLabel().equals(vertex)) {
                include = true;
            }
        }
        if(!include) {
            vertices.add(new Vertex<L>(vertex));
        }
        checkRep();
        return include;
    }

    @Override
    public int set(L source, L target, int weight) {
        int previouWeight = 0;
        this.add(source);
        this.add(target);
        for(Vertex<L> eachVertex: vertices) {
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
    public boolean remove(L vertex) {
        boolean include = false;
        Iterator<Vertex<L>> vertexIterator = vertices.iterator();
        while (vertexIterator.hasNext()) {
            Vertex<L> vertexNext = vertexIterator.next();
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
    public Set<L> vertices() {
        Set<L> labelSet = new HashSet<>();
        for(Vertex<L> vertex : vertices) {
            labelSet.add(vertex.getLabel());
        }
        
        return labelSet;
    }

    @Override
    public Map<L, Integer> sources(L target) {
        checkRep();
        for(Vertex<L> vertex:vertices) {
            if(vertex.getLabel().equals(target)) {
                return vertex.sourcesMap();
            }
        }
        return new HashMap<>();
    }

    @Override
    public Map<L, Integer> targets(L source) {
        checkRep();
        for(Vertex<L> vertex:vertices) {
            if(vertex.getLabel().equals(source)) {
                return vertex.targetsMap();
            }
        }
        return new HashMap<>();
    }

    @Override
    public String toString() {
        checkRep();
        String result = "";
        for(Vertex<L> vertex: vertices) {
               result += vertex.getLabel() + "\n";
               for (Map.Entry<L, Integer> entry : vertex.targetsMap().entrySet()) {
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
class Vertex<L> {

    private L vertex;
    private Map<L, Integer> sources;
    private Map<L, Integer> targets;

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
    public Vertex(L source) {
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
        for (Map.Entry<L, Integer> entry : targets.entrySet()) {
            assert entry.getValue() > 0;
        }
        for (Map.Entry<L, Integer> entry : sources.entrySet()) {
            assert entry.getValue() > 0;
        }
    }

    /**
     * @return source value
     */
    public L getLabel() {
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
    public int setTarget(L target, Integer weighted) {
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
    public int setSource(L source, Integer weighted) {
        
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
    public Map<L, Integer> targetsMap() {
        checkRep();
        return new HashMap<L, Integer>(targets);
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
    public Map<L, Integer> sourcesMap() {
        checkRep();
        return new HashMap<L, Integer>(sources);
    };

    @Override
    public String toString() {
        checkRep();
        String result = vertex + "\n";
        for (Map.Entry<L, Integer> entry : targets.entrySet()) {
            result += "---" + entry.getValue() + "---> " + entry.getKey() + "\n";
        }
        for (Map.Entry<L, Integer> entry : sources.entrySet()) {
            result += "---" + entry.getValue() + "---> " + entry.getKey() + "\n";
        }
        return result;
    }

}
