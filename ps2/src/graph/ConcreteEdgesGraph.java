/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   represents a weighted directed graph with distinct vertices
    //   and positive weight edges.
    // Representation invariant:
    //   for each edge in edges, there are definitely two vertices in vertices set
    // Safety from rep exposure:
    //   All fields are private and final; 
    
    /**
     * Make an empty graph
     */
    public ConcreteEdgesGraph() {
        checkRep();
    }
    
    /**
     * Check that the rep invariant is true
     */
    private void checkRep() {
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource());
            assert vertices.contains(edge.getTarget());
        }
    }
    
    @Override public boolean add(String vertex) {
        return vertices.add(vertex);
        // throw new RuntimeException("not implemented");
    }
    
    @Override public int set(String source, String target, int weight) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public boolean remove(String vertex) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Set<String> vertices() {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<String, Integer> sources(String target) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<String, Integer> targets(String source) {
        throw new RuntimeException("not implemented");
    }
    
    // TODO toString()
    @Override public String toString() {
        throw new RuntimeException("not implemented");
    }
    
}

/**
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * This immutable data type represents an edge in a weight directed graph.
 * It contains source, target vertices and weight as representation.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge {
    
    private final String source;
    private final String target;
    private final int weight;
    
    
    // Abstraction function:
    //   represents the source and target vertices of an edge with its
    //   corresponding weight.
    // Representation invariant:
    //   weight should be positive
    //   source and target cannot be the same.
    // Safety from rep exposure:
    //   All fields are private and final;
    //   source and target are Strings, so are guaranteed immutable;
    
    /**
     * Make a edge.
     * @param source the vertex directed from
     * @param target the vertex directed to
     * @param weight the weight of the edge
     */
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    /**
     * Check that the rep invariant is true
     */
    private void checkRep() {
        assert !source.equals(target);
        assert weight > 0;    
    }
    
    /**
     * @return the label of the vertex
     */
    public String getSource(){
        checkRep();
        return source;
    }
    
    /**
     * @return the label of the target vertex
     */
    public String getTarget(){
        checkRep();
        return target;
    }
    
    /**
     * @return the weight of this edge
     */
    public int getWeight(){
        checkRep();
        return weight;
    }
    
    @Override
    public String toString() {
        checkRep();
        return this.source + " --- " + this.weight + " ---> " + this.target;
    }
}
