/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //add():
    //  Graph size = 1, n
    //  Graph = produced by emptyInstance(), produced by vertices()
    //set(): 
    //  Graph size = 0, n
    //  Graph = produced by emptyInstance(), produced by vertices()
    //remove(): 
    //  Graph size = 1, n
    //  i = 0, middle, size-1
    //  Graph = produced by emptyInstance(), produced by vertices()
    //vertices():
    //  Graph size = 0, 1, n
    //  string = produced by emptyInstance(), produced by vertices()
    //sources
    //  
    //targets

    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    @Test
    public void testInitialVerticesAddRemoveEmpty() {
        Graph<String> test1 =  emptyInstance();
        test1.add("aa");
        assertTrue("expected new graph to have a vertice",
                 test1.vertices().contains("aa"));
        test1.remove("aa");
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), test1.vertices());
    }
    
    @Test
    public void testInitialVerticesAddSameRemove() {
        Graph<String> test2 =  emptyInstance();
        test2.add("aa");
        test2.add("aa");
        test2.remove("aa");
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), test2.vertices());
    }
    
    @Test
    public void testInitialVerticesAddTwoSet() {
        Graph<String> test3 =  emptyInstance();
        test3.add("aa");
        test3.add("bb");
        assertEquals("expected new graph to have two vertices",
                2, test3.vertices().size());
        emptyInstance().remove("aa");
        emptyInstance().remove("bb");
    }
    
    @Test
    public void testInitialVerticesAddTwoSetzeroSourceTarget() {
        Graph<String> test4 =  emptyInstance();
        test4.add("aa");
        test4.add("bb");
        test4.set("aa", "bb", 0);
        test4.set("bb", "aa", 1);
        assertEquals("expected new graph to have no vertices",
                0, test4.sources("bb").size());
        assertEquals("expected new graph to have no vertices",
                1, (int) test4.sources("aa").get("bb"));
        emptyInstance().remove("bb");
        assertEquals("expected new graph to have no vertices",
                0, test4.targets("aa").size());
    }
    
    @Test
    public void testInitialVerticesAddThreeSetTwoSourceTarget() {       
        Graph<String> test5 =  emptyInstance();
        test5.set("aa", "bb", 1);
        test5.set("bb", "aa", 2);
        test5.set("bb", "cc", 1);
        assertEquals("expected new graph to have 2 targets",
                2, test5.targets("bb").size());
        assertEquals("expected new graph to have 2 targets",
                2, (int) test5.targets("bb").get("aa"));
        assertEquals("expected new graph to have 2 targets",
                1, (int) test5.targets("bb").get("cc"));
        
    }
    
    @Test
    public void testInitialVerticesSetTwoSourceTarget() {       
        Graph<String> test6 =  emptyInstance();
        test6.set("aa", "bb", 1);
        test6.set("aa", "bb", 0);
        assertEquals("expected new graph to have 1 target",
                0, test6.sources("bb").size());
        assertEquals("expected new graph to have 1 target",
                0, (int) test6.targets("aa").size());  
    }
    
    @Test
    public void testInitialVerticesAddThreeRemoveMid() {       
        Graph<String> test7 =  emptyInstance();
        test7.set("bb", "aa", 2);
        test7.set("bb", "cc", 1);
        test7.set("aa", "bb", 0);
        test7.remove("bb");
        assertEquals("expected new graph to have 2 vertices",
                2, test7.vertices().size());
        assertEquals("expected new graph to have 2 vertices",
                0, test7.targets("cc").size());
        assertEquals("expected new graph to have 2 vertices",
                        0, test7.targets("aa").size());
    }
    
    // Testing strategy for GraphInstanceTest.toString()
    //      edges: 0, n
    //      vertices: 0, n
    
    // tests for ConcreteEdgesGraph.toString()
    @Test
    public void testInitialVerticesEmptytoString() {
        Graph<String> test1 =  emptyInstance();
        assertEquals("expected new graph to have no vertex",
                 0,test1.toString().length());
    }
    
    @Test
    public void testInitialMultiVerticesNoEdge() {
        Graph<String> test2 =  emptyInstance();
        test2.add("aa");
        test2.add("bb");
        String[] stringSplitted = test2.toString().split("\n");
        assertTrue("expected new graph to have vertives",
                Arrays.asList(stringSplitted)
                .containsAll(Arrays.asList("aa","bb")));
    }
    
    @Test
    public void testInitialMultipleVerticesMultipleEdge() {
        Graph<String> test3 =  emptyInstance();
        test3.set("aa", "bb", 2);
        test3.set("bb", "aa", 1);
        test3.set("bb", "cc", 1);
        test3.add("dd");
        String[] stringSplitted = test3.toString().split("\n");
        assertTrue("expected new graph to have vertives",
                Arrays.asList(stringSplitted)
                .containsAll(Arrays.asList("aa"
                        ,"bb"
                        ,"cc"
                        ,"dd"
                        ,"aa ---2---> bb"
                        ,"bb ---1---> aa"
                        ,"bb ---1---> cc")));
    }
    
}
