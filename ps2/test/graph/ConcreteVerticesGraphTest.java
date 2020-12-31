/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   TODO
    
    // TODO tests for operations of Vertex
    @Test
    public void testInitialVertexnoEdge() {
        Vertex vertxTest = new Vertex("A");
        vertxTest.setTarget("B", 0);
        assertEquals("expected new vertex to have no edge",
                0, vertxTest.targetsMap().size());
    }
    
    // TODO other tests for instance methods of Graph
    @Test
    public void testInitialVerticesAddRemove() {
        Vertex vertxTest = new Vertex("A");
        vertxTest.setTarget("B", 1);
        assertEquals("expected new vertex toString",
                "A\n---1---> B\n", vertxTest.toString());
        vertxTest.setTarget("B", 0);
        assertEquals("expected new graph to have no edge",
                0, vertxTest.targetsMap().size());
    }
    
    @Test
    public void testInitialVerticesAddSameRemove() {
        Vertex vertxTest = new Vertex("A");
        vertxTest.setTarget("B", 1);
        vertxTest.setTarget("B", 2);
        assertEquals("expected new vertex toString",
                "A\n---2---> B\n", vertxTest.toString());
    }
    
    
    @Test
    public void testInitialVerticesAddThree() {       
        Vertex vertxTest = new Vertex("A");
        vertxTest.setTarget("B", 1);
        vertxTest.setTarget("C", 2);
        assertTrue("expected new vertex to have two targets",
                vertxTest.targetsMap().keySet().containsAll(Arrays.asList("B","C")));
        assertEquals("expected new vertex have size of target B ",
                1 , (int) vertxTest.targetsMap().get("B"));
        assertEquals("expected new vertex have size of target B ",
                2 , (int) vertxTest.targetsMap().get("C"));
    }
    
  
    
 
    
}
