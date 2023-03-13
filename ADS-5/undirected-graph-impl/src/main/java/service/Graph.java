package service;

import model.GraphEdge;
import model.GraphVertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains the actual implementation of the undirected graph using the adjacency list approach
 * The created graph will have two types of vertices and undirected links between vertices of different types. Note that
 * the supported graph will effectively be a bipartite graph (effectively meaning that there will be no edges between
 * vertices of the same type)
 * */

public class Graph<V1 extends GraphVertex<V1>, V2 extends GraphVertex<V2>> {

    /* Two hashmaps store the two types of vertices stored within the graph.
    A hashmap has been used here in place of a list to enable quick retrieval of the vertex data given a vertex id.
     */
    private HashMap<String, V1> type1Vertices;
    private HashMap<String, V2> type2Vertices;

    /*
    * These two hashmaps store edge information. Note that since we have an undirected graph we will store the edges
    * in both forward and reverse directions. For instance, if we have an edge between vertices A & B,the same is recorded as
    * a forward edge between vertex A and B and a reverse edge between vertex B and A. This makes it possible to
    * navigate from either vertex to the other one using the same edge.
    * */
    HashMap<String, List<String>> forwardEdges;
    HashMap<String, List<String>> reverseEdges;

    /**
     * @param type1Vertices The list of the first type of vertices to be stored within the graph
     * @param type2Vertices The list of the second type of vertices to be stored within the graph
     * @param edges  type2Vertices The list of edges between the type 1 and type 2 vertices
     *
     * */
    public Graph(List<V1> type1Vertices, List<V2> type2Vertices, List<GraphEdge> edges){
        this.type1Vertices = new HashMap<>();
        this.type2Vertices = new HashMap<>();

        /*
        * First store the vertices in the relevant hashmaps for quick retrieval
        * */
        for(V1 v: type1Vertices){
            this.type1Vertices.put(v.getVertexId(), v);
        }

        for(V2 v: type2Vertices){
            this.type2Vertices.put(v.getVertexId(), v);
        }

        /*
         * Store the edges of the graph in the adjacency list notation. For an edge between vertex A and vertex B,
         * record a forward edge between vertices A and B and a reverse edge between vertex B and A. This is done
         * since the implementation corresponds to an undirected graph.
         * */
        this.forwardEdges = new HashMap<>();
        this.reverseEdges = new HashMap<>();

        for(GraphEdge edge: edges){
            if(!this.forwardEdges.containsKey(edge.getEdgeSource()))
                this.forwardEdges.put(edge.getEdgeSource(), new ArrayList<>());

            this.forwardEdges.get(edge.getEdgeSource()).add(edge.getEdgeDest());

            if(!this.reverseEdges.containsKey(edge.getEdgeDest()))
                this.reverseEdges.put(edge.getEdgeDest(), new ArrayList<>());

            this.reverseEdges.get(edge.getEdgeDest()).add(edge.getEdgeSource());
        }
    }

    /**
     * @param vertexId This refers to an identifier for a vertex of type 1
     * @return The list of type 2 vertices that the provided type 1 vertex is connected to
     * */
    public List<V2> getConnectionsFromType1Vertex(String vertexId){
        List<String > edges = this.forwardEdges.getOrDefault(vertexId, new ArrayList<>());
        return edges.stream().map(e -> this.type2Vertices.get(e)).collect(Collectors.toList());
    }

    /**
     * @param vertexId This refers to an identifier for a vertex of type 2
     * @return The list of type 1 vertices that the provided type 2 vertex is connected to
     * */
    public List<V1> getConnectionsFromType2Vertex(String vertexId){
        List<String> edges = this.reverseEdges.getOrDefault(vertexId, new ArrayList<>());
        return edges.stream().map(e -> this.type1Vertices.get(e)).collect(Collectors.toList());
    }

    /**
     * @param vertexId This refers to an identifier for a vertex of type 1
     * @return The vertex of type 1 corresponding to the provided vertex id. null if a vertex with the said id is not present
     * */
    public V1 getType1Vertex(String vertexId){
        return this.type1Vertices.get(vertexId);
    }

    /**
     * @param vertexId This refers to an identifier for a vertex of type 2
     * @return The vertex of type 2 corresponding to the provided vertex id. null if a vertex with the said id is not present
     * */
    public V2 getType2Vertex(String vertexId){
        return this.type2Vertices.get(vertexId);
    }
}
