/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Arrays;

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
    
    private static final String ver1 = "ning";
    private static final String ver2 = "Jack";
    private static final String ver3 = "mike";
    
    private static final int weight1 = 4;
    private static final int weight2 = 5;
    
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
    // toString():
    //      vertices = 0, 1, n
    
    // Testing ConcreteVerticesGraph.toString()
    
    

    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   getLabel()
    //   getSources()
    //      sources = 0, n
    //   getTargets()
    //      targets = 0, n
    //   setSources()
    //      vertex = exists, not exists
    //      weight = 0, positive
    
    // Testing getLabel()
    @Test
    public void testGetLabel() {
        Vertex vertex1 = new Vertex(ver1);
        
        assertEquals("expected label 'ning'", "ning", vertex1.getLabel());
    }
    
    // Testing getSources() 0 | n
    @Test
    public void testgetSources() {
        Vertex vertex1 = new Vertex(ver1);
        
        assertTrue("expected empty true", vertex1.getSources().isEmpty());
        vertex1.setSources(ver2, weight1);
        vertex1.setSources(ver3, weight2);
        assertTrue("expected sources contains 'Jack' and 'mike'", vertex1.getSources().keySet().containsAll(Arrays.asList(ver1, ver2)));   
    }
    
    // Testing getTargets() 0 | n
    @Test
    public void testGetTargets() {
        Vertex vertex1 = new Vertex(ver1);
        
        assertTrue("expected empty true", vertex1.getTargets().isEmpty());
        vertex1.setTargets(ver2, weight1);
        vertex1.setTargets(ver3, weight2);
        assertTrue("expected sources contains 'Jack' and 'mike'", vertex1.getTargets().keySet().containsAll(Arrays.asList(ver1, ver2))); 
    }
    
    // Testing setSources() weight = 0 & (vertex not exists | exists)
    @Test
    public void testSetSourcesZeroWeight() {
        Vertex vertex1 = new Vertex(ver1);
        vertex1.setSources(ver2, 0);
        
        assertTrue("expected empty true", vertex1.getSources().isEmpty());
        assertEquals("expected return 0", 0, (int) vertex1.setSources(ver2, weight1));
        assertEquals("expected vertex 1 sources contains vertex 2 with a weight 4", 4, (int) vertex1.getSources().get(ver2));
        vertex1.setSources(ver2, 0);
        assertTrue("expected empty true", vertex1.getSources().isEmpty());
    }
    
    // Testing setSources() weight = positive & (vertex not exists | exists)
    @Test
    public void testSetSourcesPositiveWeight() {
        Vertex vertex1 = new Vertex(ver1);
        
        assertEquals("expected return 0", 0, (int) vertex1.setSources(ver2, weight1));
        assertFalse("expected empty false", vertex1.getSources().isEmpty());
        assertEquals("expected vertex 1 sources contains vertex 2 with a weight 4", 4, (int) vertex1.getSources().get(ver2));
        vertex1.setSources(ver2, weight2);
        assertEquals("expected vertex 1 sources contains vertex 2 with a weight 5", 5, (int) vertex1.getSources().get(ver2));
    }
    
    // Testing setTargets() weight = 0 & (vertex not exists | exists)
    @Test
    public void testSetTargetsZeroWeight() {
        Vertex vertex1 = new Vertex(ver1);
        vertex1.setTargets(ver2, 0);
        
        assertTrue("expected empty true", vertex1.getTargets().isEmpty());
        assertEquals("expected return 0", 0, (int) vertex1.setTargets(ver2, weight1));
        assertEquals("expected vertex 1 targets contains vertex 2 with a weight 4", 4, (int) vertex1.getTargets().get(ver2));
        vertex1.setTargets(ver2, 0);
        assertTrue("expected empty true", vertex1.getTargets().isEmpty());
    }
    
    // Testing setTargets() weight = positive & (vertex not exists | exists)
    @Test
    public void testSetTargetsPositiveWeight() {
        Vertex vertex1 = new Vertex(ver1);
        
        assertEquals("expected return 0", 0, (int) vertex1.setTargets(ver2, weight1));
        assertFalse("expected empty false", vertex1.getTargets().isEmpty());
        assertEquals("expected vertex 1 targets contains vertex 2 with a weight 4", 4, (int) vertex1.getTargets().get(ver2));
        vertex1.setTargets(ver2, weight2);
        assertEquals("expected vertex 1 targets contains vertex 2 with a weight 5", 5, (int) vertex1.getTargets().get(ver2));
    }
}
