/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
    
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Testing ConcreteEdgesGraph implementation
    // Test empty
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }
    
    // Test Integer type
    @Test
    public void testTypeInteger() {
        Graph<Integer> IntGraph = Graph.empty();
        
        assertEquals("expected previous weight is 0", 0, IntGraph.set(4, 5, 2));
        assertEquals("expected current weight of label 4 and 5 is 2", 2, (int) IntGraph.sources(5).get(4));
        assertEquals("expected previous weight is 0", 0, IntGraph.set(3, 5, 3));
        assertEquals("expected current weight of label 3 and 5 is 3", 3, (int) IntGraph.sources(5).get(3));
        assertEquals("expected size of vertices is 3", 3, IntGraph.vertices().size());
    }
}
