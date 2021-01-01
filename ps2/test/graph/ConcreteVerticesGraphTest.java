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
        return new ConcreteVerticesGraph<>();
    }
    
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
        Vertex<String> vertex1 = new Vertex<>(ver1);
        
        assertEquals("expected label 'ning'", "ning", vertex1.getLabel());
    }
    
    // Testing getSources() 0 | n
    @Test
    public void testgetSources() {
        Vertex<String> vertex1 = new Vertex<>(ver1);
        
        assertTrue("expected empty true", vertex1.getSources().isEmpty());
        vertex1.setSources(ver2, weight1);
        vertex1.setSources(ver3, weight2);
        assertTrue("expected sources contains 'Jack' and 'mike'", vertex1.getSources().keySet().containsAll(Arrays.asList(ver2, ver3)));   
    }
    
    // Testing getTargets() 0 | n
    @Test
    public void testGetTargets() {
        Vertex<String> vertex1 = new Vertex<>(ver1);
        
        assertTrue("expected empty true", vertex1.getTargets().isEmpty());
        vertex1.setTargets(ver2, weight1);
        vertex1.setTargets(ver3, weight2);
        assertTrue("expected sources contains 'Jack' and 'mike'", vertex1.getTargets().keySet().containsAll(Arrays.asList(ver2, ver3))); 
    }
    
    // Testing setSources() weight = 0 & (vertex not exists | exists)
    @Test
    public void testSetSourcesZeroWeight() {
        Vertex<String> vertex1 = new Vertex<>(ver1);
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
        Vertex<String> vertex1 = new Vertex<>(ver1);
        
        assertEquals("expected return 0", 0, (int) vertex1.setSources(ver2, weight1));
        assertFalse("expected empty false", vertex1.getSources().isEmpty());
        assertEquals("expected vertex 1 sources contains vertex 2 with a weight 4", 4, (int) vertex1.getSources().get(ver2));
        vertex1.setSources(ver2, weight2);
        assertEquals("expected vertex 1 sources contains vertex 2 with a weight 5", 5, (int) vertex1.getSources().get(ver2));
    }
    
    // Testing setTargets() weight = 0 & (vertex not exists | exists)
    @Test
    public void testSetTargetsZeroWeight() {
        Vertex<String> vertex1 = new Vertex<>(ver1);
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
        Vertex<String> vertex1 = new Vertex<>(ver1);

        assertEquals("expected return 0", 0, (int) vertex1.setTargets(ver2, weight1));
        assertFalse("expected empty false", vertex1.getTargets().isEmpty());
        assertEquals("expected vertex 1 targets contains vertex 2 with a weight 4", 4, (int) vertex1.getTargets().get(ver2));
        vertex1.setTargets(ver2, weight2);
        assertEquals("expected vertex 1 targets contains vertex 2 with a weight 5", 5, (int) vertex1.getTargets().get(ver2));
    }
    
    // Testing Edge.toString()
    @Test
    public void testVertexToString() {
        Vertex<String> vertex1 = new Vertex<>(ver1);
        vertex1.setSources(ver2, weight1);
        vertex1.setTargets(ver3, weight2);
        
        assertEquals("expected toString", "Jack --- 4 ---> ning\nning --- 5 ---> mike\n", vertex1.toString());
    }
}
