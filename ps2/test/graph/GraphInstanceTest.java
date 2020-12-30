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
    //   
    // add():
    //      graph = 0, N
    //      L vertex = exists, not exists
    // set():
    //      graph = 0, N
    //      L source = exists, not exists
    //      L target = exists, not exists
    //      int weight = 0, positive
    // remove():
    //      graph = 0, N
    //      L vertex = exists, not exists
    // vertices():
    //      graph = 0, N
    // sources():
    //      graph = 0, N
    //      L target = exists, not exists
    //      L source = 0, N
    // targets()
    //      graph = 0, N
    //      L source = exists, not exists
    //      L target = 0, N
    // ConcreteEdgesGraph.toString() and 
    // toString():
    //      edges = 0, n
    //      vertices = 0, 1, n
    
    private static final String ver1 = "ver1";
    private static final String ver2 = "ver2";
    private static final String ver3 = "ver3";
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
    
    // Empty graph & not exists
    @Test
    public void testAddEmptyGraphNotExists() {
        Graph<String> instance = emptyInstance();
        boolean addIn1 = instance.add(ver1);
        
        assertTrue("expected true", addIn1);
        assertEquals("expected graph size", 1, instance.vertices().size());
    }
    
    // N graph & not exists
    @Test
    public void testAddMultipleGraphNotExists() {
        Graph<String> instance = emptyInstance();
        boolean addIn1 = instance.add(ver1);
        boolean addIn2 = instance.add(ver2);
        boolean addIn3 = instance.add(ver3);
        
        assertTrue("expected add first vertex successfully true", addIn1);
        assertTrue("expected add second vertex successfully true", addIn2);
        assertTrue("expected add third vertex successfully true", addIn3);
        assertEquals("expected graph size", 3, instance.vertices().size());
    }
    
    // Empty graph & exists
    @Test
    public void testAddMultipleGraphExists() {
        Graph<String> instance = emptyInstance();
        boolean addIn1 = instance.add(ver1);
        boolean addIn2 = instance.add(ver2);
        boolean addIn3 = instance.add(ver2);
        
        assertTrue("expected add first vertex successfully true", addIn1);
        assertTrue("expected add second vertex successfully true", addIn2);
        assertFalse("expected add third vertex successfully true", addIn3);
        assertEquals("expected graph size", 2, instance.vertices().size());
    }
    
    
    
    
    
    // Empty graph & source not exists & target not exists & weight 0 | positive
    @Test
    public void testSetEmptyGraphSourceTargetNotExistsWeightZeroOrPositive() {
        Graph<String> instance = emptyInstance();
      
        assertEquals("expected graph size", 0, instance.vertices().size());
    }
    
    // N graph & source exists & target exists & weight 0 | positive
    @Test
    public void testSetMultipleGraphSourceTargetExistsWeightZeroOrPositive() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
        instance.set(ver1, ver2, 2);
           
        assertEquals("expected previous weight", 2, instance.set(ver1, ver2, 3));
        assertTrue("expected source ver2 contains ver1", instance.sources(ver2).containsKey(ver1));
        assertEquals("expected current edge weight", 3, (int) instance.sources(ver2).get(ver1));
        assertEquals("expected previous weight", 3, instance.set(ver1, ver2, 0));
        assertFalse("expected source ver2 doesn't contains ver1", instance.sources(ver2).containsKey(ver1));
    }
    
    // N graph & source exists & target not exists & weight 0 | positive
    @Test
    public void testSetMultipleGraphSourceExistsTargetNotExistsWeightZeroOrPositive() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
           
        assertEquals("expected previous weight", 0, instance.set(ver1, ver3, 0));
        assertFalse("expected source ver3 doesn't contains ver1", instance.sources(ver3).containsKey(ver1));
        assertEquals("expected previous weight", 0, instance.set(ver1, ver3, 2));
        assertTrue("expected source ver3 contains ver1", instance.sources(ver3).containsKey(ver1));
        assertEquals("expected current edge weight", 2, (int) instance.sources(ver3).get(ver1));
    }
    
    // N graph & source not exists & target exists & weight 0 | positive
    @Test
    public void testSetMultipleGraphSourceNotExistsTargetExistsWeightZeroOrPositive() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
           
        assertEquals("expected previous weight", 0, instance.set(ver3, ver2, 0));
        assertFalse("expected source ver2 doesn't contains ver3", instance.sources(ver2).containsKey(ver3));
        assertEquals("expected previous weight", 0, instance.set(ver3, ver2, 2));
        assertTrue("expected source ver2 contains ver3", instance.sources(ver2).containsKey(ver3));
        assertEquals("expected current edge weight", 2, (int) instance.sources(ver2).get(ver3));
    }
    
    // N graph & source not exists & target not exists & weight 0 | positive
    @Test
    public void testSetMultipleGraphSourceTargetNotExistsWeightZeroOrPositive() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
           
        assertEquals("expected previous weight", 0, instance.set(ver2, ver3, 0));
        assertFalse("expected source ver3 doesn't contains ver2", instance.sources(ver3).containsKey(ver2));
        assertEquals("expected previous weight", 0, instance.set(ver2, ver3, 2));
        assertTrue("expected source ver3 contains ver2", instance.sources(ver3).containsKey(ver2));
        assertEquals("expected current edge weight", 2, (int) instance.sources(ver3).get(ver2));
    }
    
    
    
    
    
    
    // Empty graph & vertex = (exists | not exists)
    @Test
    public void testRemoveEmptyGraphVertexExistsOrNotExists() {
        Graph<String> instance = emptyInstance();
        
        assertEquals("expected graph size", 0, instance.vertices().size());
    }
    
    // N graph & vertex = exists
    @Test
    public void testRemoveMultipleGraphVertexExists() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
        instance.set(ver1, ver2, 4);
        instance.set(ver2, ver1, 3);
        
        assertTrue("expected result true", instance.remove(ver1));
        assertFalse("expected ver1 doesn't exists in graph", instance.vertices().contains(ver1));
    }
    
    // N graph & vertex = not exists
    @Test
    public void testRemoveMultipleGraphVertexNotExists() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
        instance.set(ver1, ver2, 4);
        instance.set(ver2, ver1, 3);
        
        assertFalse("expected result false", instance.remove(ver3));
        assertFalse("expected ver1 doesn't exists in graph", instance.vertices().contains(ver3));
    }
    
    
    
    
    
    // Empty graph
    @Test
    public void testEmptyVertices() {
        Graph<String> instance = emptyInstance();
        
        assertTrue("expected result true", instance.vertices().isEmpty());
    }
    
    // Multiple graph
    @Test
    public void testMultipleVertices() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
        
        assertFalse("expected result false", instance.vertices().isEmpty());
        assertEquals("expected number of vertices", 2, instance.vertices().size());
    }
    
    
    
    
    
    
    // Empty graph & target = not exists => source = 0
    @Test
    public void testSourcesEmptyGraphTargetNotExists() {
        Graph<String> instance = emptyInstance();
        
        assertTrue("expected result true", instance.sources(ver3).isEmpty());
    }
    
    // N graph & target = not exists => source = 0
    @Test
    public void testSourcesMultipleGraphTargetNotExists() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
        instance.set(ver2, ver1, 4);
        instance.set(ver3, ver1, 2);
        
        assertTrue("expected result true", instance.sources(ver3).isEmpty());
    }
    
    // N graph & target = exists => source = 0
    @Test
    public void testSourcesMultipleGraphTargetExistsZeroSources() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
        instance.add(ver3);
        instance.set(ver2, ver1, 4);
        instance.set(ver1, ver2, 2);
        
        assertEquals("expected number of sources", 0, instance.sources(ver3).size());
    }
    
    // N graph & target = exists => source = multiple
    @Test
    public void testSourcesMultipleGraphTargetExistsMultipleSources() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
        instance.add(ver3);
        instance.set(ver2, ver1, 4);
        instance.set(ver3, ver1, 2);
        
        assertEquals("expected number of sources", 2, instance.sources(ver1).size());
        assertTrue("expected contains ver2", instance.sources(ver1).containsKey(ver2));
        assertTrue("expected contains ver3", instance.sources(ver1).containsKey(ver3));
    }
    
    
    
    
    

    // Empty graph & source = not exists => target = 0
    @Test
    public void testTargetsEmptyGraphSourceNotExists() {
        Graph<String> instance = emptyInstance();
        
        assertTrue("expected result true", instance.targets(ver3).isEmpty());
    }
    
    // N graph & source = not exists => target = 0
    @Test
    public void testTargetsMultipleGraphSourceNotExists() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
        instance.set(ver1, ver2, 4);
        instance.set(ver2, ver1, 2);
        
        assertTrue("expected result true", instance.targets(ver3).isEmpty());
    }
    
    // N graph & source = exists => target = 0
    @Test
    public void testTargetsMultipleGraphSourceExistsZeroTargets() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
        instance.add(ver3);
        instance.set(ver2, ver1, 4);
        instance.set(ver1, ver2, 2);
        
        assertEquals("expected number of sources", 0, instance.targets(ver3).size());
    }
    
    // N graph & source = exists => target = multiple
    @Test
    public void testTargetsMultipleGraphSourceExistsMultipleTargets() {
        Graph<String> instance = emptyInstance();
        instance.add(ver1);
        instance.add(ver2);
        instance.add(ver3);
        instance.set(ver1, ver2, 4);
        instance.set(ver1, ver3, 2);
        
        assertEquals("expected number of sources", 2, instance.targets(ver1).size());
        assertTrue("expected contains ver2", instance.targets(ver1).containsKey(ver2));
        assertTrue("expected contains ver3", instance.targets(ver1).containsKey(ver3));
    }
    
    
    
    
    
    
    @Test
    public void testEmptyGraphToString() {
        Graph<String> graph = emptyInstance();
        assertEquals("expected string representation", "vertices:\nedges:\n", graph.toString());
    }

    @Test
    public void testSingleVertexToString() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertEquals("expected string representation", "vertices:\nA\nedges:\n");
    }

    @Test
    public void testMultipleVerticesEdgesToString() {
        Graph<String> graph = emptyInstance();
        graph.set("A", "B", 5);
        graph.set("A", "C", 3);
        graph.set("D", "A", 4);
        String stringRep = graph.toString();
        String[] lines = stringRep.split("\n");
        String[] labels = lines[1].split("\\s");

        assertEquals("expected number of lines", 6, lines.length);
        assertEquals("expected first line", "vertices:", lines[0]);
        assertEquals("expected third line", "edges:", lines[2]);
        assertEquals("expeted number of labels", 4, labels.length);
        assertTrue("expeted labels", Arrays.asList(labels).containsAll(Arrays.asList("A", "B", "C", "D")));
        assertTrue("expeted lines",
                Arrays.asList(lines).containsAll(Arrays.asList("A --- 5 ---> B", "A --- 3 ---> C", "D --- 4---> A")));
    }  
}
