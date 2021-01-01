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
    
    private static final String ver1 = "ning";
    private static final String ver2 = "Jack";
    
    private static final int weight1 = 4;
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<>();
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //     getSource()
    //     getTarget()
    //     getWeight()
    @Test
    public void testGetMethods() {
        Edge<String> edges = new Edge<>(ver1, ver2, weight1);
        
        assertEquals("expected string 'Jack'", edges.getTarget(), "Jack");
        assertEquals("expected string 'ning'", edges.getSource(), "ning");
        assertEquals("expected string '4'", edges.getWeight(), 4);
    }
    
    // Testing Edge.toString()
    @Test
    public void testEdgeToString() {
        Edge<String> edge = new Edge<>(ver1, ver2, weight1);
        
        assertEquals("expected toString", "ning --- 4 ---> Jack\n", edge.toString());
    }
}
