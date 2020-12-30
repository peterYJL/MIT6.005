/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   represents a weighted directed graph with distinct vertices
    //   and positive weight edges. 
    // Representation invariant:
    //   No repeat! each vertices should have different labels.
    //   vertices is a list of vertices that contains in this graph.
    //   for any two vertices A and B, if A is a source of B, then
    //   B is a target of A
    // Safety from rep exposure:
    //   All fields are private and final.
    //   make defensive copies to avoid sharing the rep's vertices object with clients.
    
    /**
     * Make an empty graph
     */
    public ConcreteVerticesGraph() {
        checkRep();
    }
    
    /**
     * Check that the rep invariant is true 
     */
    public void checkRep() {
        Set<Vertex> verticesSet = new HashSet<>();
        for (Vertex ver : vertices) {
            assert verticesSet.add(ver);  
        }
        
        for (Vertex ver : vertices) {
            Map<String, Integer> tempSources = ver.getSources();
            Map<String, Integer> tempTargets = ver.getTargets();
            
            for (String vertexLabel : tempSources.keySet()) {
                for (Vertex vert : vertices) {
                    if (vert.getLabel().equals(vertexLabel)) {
                        assert vert.getTargets().containsKey(vertexLabel);
                        assert vert.getTargets().get(vertexLabel).equals(tempSources.get(vert.getLabel()));
                        break;
                    }
                }
            }
            
            for (String vertexLabel : tempTargets.keySet()) {
                for (Vertex vert : vertices) {
                    if (vert.getLabel().equals(vertexLabel)) {
                        assert vert.getSources().containsKey(vertexLabel);
                        assert vert.getSources().get(vertexLabel).equals(tempTargets.get(vert.getLabel()));
                        break;
                    }
                }
            }
        }
    }

    @Override public boolean add(String vertex) {
        checkRep();
        throw new RuntimeException("not implemented");
    }
    
    @Override public int set(String source, String target, int weight) {
        checkRep();
        throw new RuntimeException("not implemented");
    }
    
    @Override public boolean remove(String vertex) {
        checkRep();
        throw new RuntimeException("not implemented");
    }
    
    @Override public Set<String> vertices() {
        checkRep();
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<String, Integer> sources(String target) {
        checkRep();
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<String, Integer> targets(String source) {
        checkRep();
        throw new RuntimeException("not implemented");
    }
    
    @Override 
    public String toString() {
        checkRep();
        throw new RuntimeException("not implemented");
    }
}

/**
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * This mutable data type represents a vertex in a weight directed graph.
 * It contain variable label as its name, and two Map sources and targets
 * which is all the vertex direct from and direct to. 
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    
    private final String label;
    private final Map<String, Integer> sources;
    private final Map<String, Integer> targets;
    
    // Abstraction function:
    //   represents a vertex with name label, and the vertices 
    //   direct to targets and directed by sources.
    // Representation invariant:
    //   the weights between sources and targets should be positive
    // Safety from rep exposure:
    //   All fields are private and final.
    //   make defensive copies to avoid rep exposure.
    
    /**
     * @param label the label of the vertex
     */
    public Vertex(String label) {
        this.label = label;
        this.sources = new HashMap<>();
        this.targets = new HashMap<>();
        checkRep();
    }
    
    /**
     * Check that the rep invariants is true
     */
    private void checkRep() {
        assert label != null;
        for (Integer weight : sources.values()) {
            assert weight > 0;
        }
        
        for (Integer weight : targets.values()) {
            assert weight > 0;
        }
    }
    
    /**
     * @return the label of the vertex
     */
    public String getLabel() {
        checkRep();
        return this.label;
    }
    
    /**
     * @return the label of the vertices that direct to the current vertex
     */
    public Map<String, Integer> getSources() {
        //return new HashMap<>(this.sources);
        checkRep();
        throw new RuntimeException("not implemented");
    }
    
    /**
     * @return the label of the vertices that the current vertex direct to
     */
    public Map<String, Integer> getTargets() {
        //return new HashMap<>(this.targets);
        checkRep();
        throw new RuntimeException("not implemented");
    }
    
    /**
     * Add, change, or remove a weighted directed edge directed from this vertex.
     * If weight is nonzero, add an edge or update the weight of that edge;
     * If weight is zero, remove the edge if it exists (the vertex is not
     * otherwise modified).
     * 
     * @param vertex the vertex we want to set with
     * @param weight the weight of the edge
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         the key to target
     */
    public Integer setSources(String source, int weight) {
        /*
        if (sources.containsKey(source)) {
            int old = this.sources.put(source, weight);
            checkRep();
            return old;
        } else {
            this.sources.put(source, weight);
            checkRep();
            return 0;
        }
        checkRep();
        */
        throw new RuntimeException("not implemented");
    }  
    
    /**
     * Add, change, or remove a weighted directed edge directed from this vertex.
     * If weight is nonzero, add an edge or update the weight of that edge;
     * If weight is zero, remove the edge if it exists (the vertex is not
     * otherwise modified).
     * 
     * @param target label of the source vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     */
    public Integer setTargets(String target, int weight) {
        /*
        if (targets.containsKey(target)) {
            int old = this.targets.put(target, weight);
            checkRep();
            return old;
        } else {
            this.targets.put(target, weight);
            checkRep();
            return 0;
        }
        checkRep();
        */
        throw new RuntimeException("not implemented");
    }  
    
    @Override
    public String toString() {
        throw new RuntimeException("not implemented");
    }
}
