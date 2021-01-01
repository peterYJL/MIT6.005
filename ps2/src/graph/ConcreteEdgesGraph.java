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
import java.util.Iterator;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
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
        for (Edge<L> edge : edges) {
            assert vertices.contains(edge.getSource());
            assert vertices.contains(edge.getTarget());
        }
    }
    
    @Override public boolean add(L vertex) {
        boolean success = vertices.add(vertex);
        checkRep();
        return success;
        // throw new RuntimeException("not implemented");
    }
    
    /**
     * Add, change, or remove a weighted directed edge in this graph.
     * If weight is nonzero, add an edge or update the weight of that edge;
     * vertices with the given labels are added to the graph if they do not
     * already exist.
     * If weight is zero, remove the edge if it exists (the graph is not
     * otherwise modified).
     * 
     * @param source label of the source vertex
     * @param target label of the target vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     */
    @Override public int set(L source, L target, int weight) {
        assert weight >= 0;
        int previous = 0;
        Iterator<Edge<L>> iter = edges.iterator();
        while (iter.hasNext()) {
            Edge<L> edge = iter.next();
            if (weight == 0) {
                if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                    previous = edge.getWeight();
                    iter.remove();
                    checkRep();
                    return previous;
                }
            } else {
                if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                    previous = edge.getWeight();
                    iter.remove();
                    edges.add(new Edge<L>(source, target, weight));
                    checkRep();
                    return previous;
                } 
            }
        }
        if (weight != 0) {
            vertices.add(source);
            vertices.add(target);
            edges.add(new Edge<L>(source, target, weight));
        }
        checkRep();
        return previous;
        // throw new RuntimeException("not implemented");
    }
    
    @Override public boolean remove(L vertex) {
        if (vertices.contains(vertex)) {
            Iterator<Edge<L>> iter = edges.iterator();
            while (iter.hasNext()) {
                Edge<L> edge = iter.next();
                if (edge.getSource().equals(vertex) || 
                    edge.getTarget().equals(vertex))
                    iter.remove();
            }
            vertices.remove(vertex);
            checkRep();
            return true;
        }
        checkRep();
        return false;
        // throw new RuntimeException("not implemented");
    }
    
    @Override public Set<L> vertices() {
        checkRep();
        return Set.copyOf(vertices);
        // throw new RuntimeException("not implemented");
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> targetSources = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getTarget().equals(target)) {
                targetSources.put(edge.getSource(), edge.getWeight());
            }
        }
        checkRep();
        return Map.copyOf(targetSources);
        // throw new RuntimeException("not implemented");
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> sourceTargets = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source)) {
                sourceTargets.put(edge.getTarget(), edge.getWeight());
            }
        }
        checkRep();
        return Map.copyOf(sourceTargets);
        // throw new RuntimeException("not implemented");
    }
    
    @Override public String toString() {
        String result = "vertices:" + vertices + "\nedges:\n";
        for (Edge<L> edge : edges) {
            result += edge.toString();
        }
        return result;
        // throw new RuntimeException("not implemented");
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
class Edge<L> {
    
    private final L source;
    private final L target;
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
    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    /**
     * Check that the rep invariant is true
     */
    private void checkRep() {
        assert !this.source.equals(this.target);
        assert this.weight > 0;    
    }
    
    /**
     * @return the label of the vertex
     */
    public L getSource(){
        checkRep();
        return this.source;
    }
    
    /**
     * @return the label of the target vertex
     */
    public L getTarget(){
        checkRep();
        return this.target;
    }
    
    /**
     * @return the weight of this edge
     */
    public int getWeight(){
        checkRep();
        return this.weight;
    }
    
    /**
     * Edge toString Override
     * @return output source --- weight ---> target
     */
    @Override
    public String toString() {
        checkRep();
        return this.source + " --- " + this.weight + " ---> " + this.target + "\n";
    }
}


