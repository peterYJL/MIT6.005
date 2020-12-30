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
    private static final String ver3 = "mike";
    
    private static final int weight1 = 4;
    private static final int weight2 = 5;
    
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
    // toString():
    //      edges = 0, n
    //      vertices = 0, 1, n
    
    // Testing ConcreteEdgesGraph.toString()
    // empty edges & (empty vertices | one vertices | multiple vertices)
    @Test
    public void testConcreteEdgesGraphToStringEmptyEdgeEmptyVertices() {
        /*
        Graph<String> graph = emptyInstance();
        
        assertEquals("expected string", "Vertices:\nEdges:\n", graph.toString());
        graph.add(ver1);
        assertEquals("expected string", "Vertices: ning\nEdges:\n", graph.toString());
        graph.add(ver2);
        assertEquals("expected string", "Vertices: ning Jack\nEdges:\n", graph.toString());
    }

    // multiple edges and multiple vertices
    @Test
    public void testConcreteEdgesGraphToStringMultipleEdgeMultipleVertices() {
        Graph<String> graph = emptyInstance();
        graph.set(ver1, ver2, weight1);
        graph.set(ver1, ver3, weight2);
        
        assertEquals("expected string", "Vertices: ning Jack mike\nEdges:\nning --- 4 ---> Jack\nning --- 5 ---> mike\n", graph.toString());
    }
    */
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //     getSource()
    //     getTarget()
    //     getWeight()
    @Test
    public void testGetMethods() {
        Edge edges = new Edge(ver1, ver2, weight1);
        
        assertEquals("expected string 'Jack'", edges.getTarget(), "Jack");
        assertEquals("expected string 'ning'", edges.getSource(), "ning");
        assertEquals("expected string '4'", edges.getWeight(), 4);
    }
    
    // Testing Edge.toString()
    @Test
    public void testEdgeToString() {
        Edge edge = new Edge(ver1, ver2, weight1);
        
        assertEquals("expected toString", "ning --- 4 ---> Jack", edge.toString());
    }
}
