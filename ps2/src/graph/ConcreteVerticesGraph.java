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
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
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
        Set<Vertex<L>> vertexSet = new HashSet<>();
        for (Vertex<L> vertex : vertices) {
            assert vertexSet.add(vertex);
        }
        
        for (Vertex<L> vertA : vertices) {
            for (Vertex<L> vertB : vertices) {
                if (vertB.getSources().containsKey(vertA.getLabel())) {
                    assert vertA.getTargets().containsKey(vertB.getLabel());
                    assert vertA.getTargets().get(vertB.getLabel()).equals(vertB.getSources().get(vertA.getLabel()));
                }
            }
        }
    }

    @Override public boolean add(L vertex) {
        for (Vertex<L> ver : vertices) {
            if (ver.getLabel().equals(vertex)) {
                return false;
            }
        }
        boolean result = vertices.add(new Vertex<L>(vertex));  
        checkRep();
        return result;
        // throw new RuntimeException("not implemented");
    }
    
    @Override public int set(L source, L target, int weight) {
        assert weight >= 0;
        int previous = 0;
        Vertex<L> sourceVer = null;
        Vertex<L> targetVer = null;
        for (Vertex<L> vertex : vertices) { 
            if (vertex.getLabel().equals(source)) {
                sourceVer = vertex;
            } else if (vertex.getLabel().equals(target)) {
                targetVer = vertex;
            }
        }
        
        if (sourceVer == null && targetVer != null) {
            vertices.add(new Vertex<L>(source));
            for (Vertex<L> vertex : vertices) { 
                if (vertex.getLabel().equals(source)) {
                    sourceVer = vertex;
                }
            }
        } else if (sourceVer != null && targetVer == null) {
            vertices.add(new Vertex<L>(target));
            for (Vertex<L> vertex : vertices) { 
                if (vertex.getLabel().equals(target)) {
                    targetVer = vertex;
                }
            }
        } else if (sourceVer == null && targetVer == null) {
            vertices.add(new Vertex<L>(source));
            vertices.add(new Vertex<L>(target));
            for (Vertex<L> vertex : vertices) { 
                if (vertex.getLabel().equals(source)) {
                    sourceVer = vertex;
                } else if (vertex.getLabel().equals(target)) {
                    targetVer = vertex;
                }
            }
        }
         
        sourceVer.setTargets(target, weight);
        previous = targetVer.setSources(source, weight);
        checkRep();
        return previous;
        // throw new RuntimeException("not implemented");
    }
    
    @Override public boolean remove(L vertex) {
        boolean result = false;
        Iterator<Vertex<L>> iter = vertices.iterator();
        while (iter.hasNext()) {
            Vertex<L> ver = iter.next();
            ver.setSources(vertex, 0);
            ver.setTargets(vertex, 0);
            if (ver.getLabel().equals(vertex)) {
                iter.remove();
                result = true;
            }
        }
        checkRep();
        return result;
        // throw new RuntimeException("not implemented");
    }
    
    @Override public Set<L> vertices() {
        Set<L> verticesSet = new HashSet<>();
        for (Vertex<L> ver : vertices) {
            verticesSet.add(ver.getLabel());
        }
        checkRep();
        return Set.copyOf(verticesSet);
        // throw new RuntimeException("not implemented");
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> sourcesMap = new HashMap<>();
        for (Vertex<L> ver : vertices) {
            if (ver.getLabel().equals(target)) {
                sourcesMap = ver.getSources();
            }
        }
        checkRep();
        return Map.copyOf(sourcesMap);
        // throw new RuntimeException("not implemented");
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> targetsMap = new HashMap<>();
        for (Vertex<L> ver : vertices) {
            if (ver.getLabel().equals(source)) {
                targetsMap = ver.getSources();
            }
        }
        checkRep();
        return Map.copyOf(targetsMap);
        // throw new RuntimeException("not implemented");
    }
    
    @Override 
    public String toString() {
        String result = "vertices:[";
        for (Vertex<L> vertex : vertices) {
            if (vertex.getLabel().equals(vertices.get(vertices.size()-1).getLabel())) {
                result += vertex.getLabel();
            } else {
                result += vertex.getLabel() + ", ";
            }
        }
        result += "]\nedges:\n";
        for (Vertex<L> vertex : vertices) {
            for (L sourceName : vertex.getSources().keySet()) {
                result += sourceName + " --- " + vertex.getSources().get(sourceName) + " ---> " + vertex.getLabel() + "\n"; 
            }
        }
        checkRep();
        return result;
        // throw new RuntimeException("not implemented");
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
class Vertex<L> {
    
    private final L label;
    private final Map<L, Integer> sources;
    private final Map<L, Integer> targets;
    
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
    public Vertex(L label) {
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
    public L getLabel() {
        checkRep();
        return this.label;
    }
    
    /**
     * @return the label of the vertices that direct to the current vertex
     */
    public Map<L, Integer> getSources() {
        checkRep();
        return Map.copyOf(this.sources);
        // throw new RuntimeException("not implemented");
    }
    
    /**
     * @return the label of the vertices that the current vertex direct to
     */
    public Map<L, Integer> getTargets() {
        checkRep();
        return Map.copyOf(this.targets);
        // throw new RuntimeException("not implemented");
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
    public Integer setSources(L source, int weight) {
        assert weight >= 0;
        Integer previous;
        if (weight == 0) {
            previous = this.sources.remove(source);
        } else {
            previous = this.sources.put(source, weight);
        }
        checkRep();
        if (previous == null) {
            return 0;
        } else {
            return previous;
        }
        // throw new RuntimeException("not implemented");
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
    public Integer setTargets(L target, int weight) {
        assert weight >= 0;
        Integer previous;
        if (weight == 0) {
            previous = this.targets.remove(target);
        } else {
            previous = this.targets.put(target, weight);
        }
        checkRep();
        if (previous == null) {
            return 0;
        } else {
            return previous;
        }
        // throw new RuntimeException("not implemented");
    }  
    
    @Override
    public String toString() {
        String result = "";
        for (L source : sources.keySet()) {
            result += source + " --- " + sources.get(source) + " ---> " + this.label + "\n";
        }
        for (L target : targets.keySet()) {
            result += this.label + " --- " + targets.get(target) + " ---> " + target + "\n";
        } 
        checkRep();
        return result;
        // throw new RuntimeException("not implemented");
    }
    
}
