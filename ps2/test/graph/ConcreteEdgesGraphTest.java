/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;


import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //      edges: 0, n
    //      vertices: 0, n
    
    // tests for ConcreteEdgesGraph.toString()
   
  
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //getSource():
    //      get source
    //getTarget():
    //      get target
    //getWeight():
    //      get weight
    //toString():
    //      get matched string
    
    @Test
    public void testInitialEdgeEmptyEdge() {
        Edge edgeTest = new Edge("A", "B", 1 ); 
        assertEquals("expected new edge toString",
                "A ---1---> B", edgeTest.toString());
    }
    
    @Test
    public void testInitialEdgeGetsource() {
        Edge edgeTest = new Edge("A", "B", 1 ); 
        assertEquals("expected new edge source",
                "A", edgeTest.getSource());
    }
    
    @Test
    public void testInitialEdgeGettarget() {
        Edge edgeTest = new Edge("A", "B", 1 ); 
        assertEquals("expected new edge target",
                "B", edgeTest.getTarget());
    }
    
    @Test
    public void testInitialEdgeGetweight() {
        Edge edgeTest = new Edge("A", "B", 1 ); 
        assertEquals("expected new edge weight",
                1, edgeTest.getWeight());
    }
}
